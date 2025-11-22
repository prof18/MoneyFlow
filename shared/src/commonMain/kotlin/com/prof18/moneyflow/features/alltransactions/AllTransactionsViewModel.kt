package com.prof18.moneyflow.features.alltransactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prof18.moneyflow.domain.entities.MoneyFlowError
import com.prof18.moneyflow.domain.entities.MoneyTransaction
import com.prof18.moneyflow.domain.repository.MoneyRepository
import com.prof18.moneyflow.presentation.MoneyFlowErrorMapper
import com.prof18.moneyflow.presentation.model.UIErrorMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AllTransactionsViewModel(
    private val moneyRepository: MoneyRepository,
    private val errorMapper: MoneyFlowErrorMapper,
) : ViewModel() {

    private var currentPage: Long = 0

    private val _state = MutableStateFlow(AllTransactionsUiState())
    val state: StateFlow<AllTransactionsUiState> = _state

    init {
        loadNextPage(reset = true)
    }

    fun mapErrorToErrorMessage(error: MoneyFlowError): UIErrorMessage {
        return errorMapper.getUIErrorMessage(error)
    }

    fun loadNextPage(reset: Boolean = false) {
        if (_state.value.isLoadingMore || _state.value.endReached) return
        viewModelScope.launch {
            _state.update { state ->
                if (reset) {
                    currentPage = 0
                    state.copy(isLoading = true, transactions = emptyList(), error = null, endReached = false)
                } else {
                    state.copy(isLoadingMore = true, error = null)
                }
            }

            try {
                val data = moneyRepository.getTransactionsPaginated(
                    pageNum = currentPage,
                    pageSize = MoneyRepository.DEFAULT_PAGE_SIZE,
                )
                val endReached = data.size < MoneyRepository.DEFAULT_PAGE_SIZE
                currentPage += 1
                _state.update { state ->
                    state.copy(
                        transactions = state.transactions + data,
                        isLoading = false,
                        isLoadingMore = false,
                        endReached = endReached,
                    )
                }
            } catch (throwable: Throwable) {
                val error = MoneyFlowError.GetAllTransaction(throwable)
                val uiError = errorMapper.getUIErrorMessage(error)
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        isLoadingMore = false,
                        error = uiError,
                    )
                }
            }
        }
    }
}

data class AllTransactionsUiState(
    val transactions: List<MoneyTransaction> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: UIErrorMessage? = null,
    val endReached: Boolean = false,
)
