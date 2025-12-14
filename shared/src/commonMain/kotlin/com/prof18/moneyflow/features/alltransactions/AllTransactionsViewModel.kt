package com.prof18.moneyflow.features.alltransactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prof18.moneyflow.data.MoneyRepository
import com.prof18.moneyflow.domain.entities.CurrencyConfig
import com.prof18.moneyflow.domain.entities.MoneyFlowError
import com.prof18.moneyflow.domain.entities.MoneyTransaction
import com.prof18.moneyflow.presentation.MoneyFlowErrorMapper
import com.prof18.moneyflow.presentation.model.UIErrorMessage
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class AllTransactionsViewModel(
    private val moneyRepository: MoneyRepository,
    private val errorMapper: MoneyFlowErrorMapper,
) : ViewModel() {

    private var currentPage: Long = 0

// TODO: migrate to a loading/error state like ICE
    private val _state = MutableStateFlow(
        AllTransactionsUiState(
            currencyConfig = CurrencyConfig(code = "", symbol = "", decimalPlaces = 2),
        ),
    )
    val state: StateFlow<AllTransactionsUiState> = _state

    init {
        loadInitialPage()
    }

    private fun loadInitialPage() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    error = null,
                    transactions = emptyList(),
                    endReached = false,
                    isLoadingMore = false,
                )
            }
            val currencyConfigDeferred = async { moneyRepository.getCurrencyConfig().first() }
            val firstPageDeferred = async {
                moneyRepository.getTransactionsPaginated(
                    pageNum = currentPage,
                    pageSize = MoneyRepository.DEFAULT_PAGE_SIZE,
                )
            }

            runCatching {
                val currencyConfig = currencyConfigDeferred.await()
                val firstPage = firstPageDeferred.await()
                val endReached = firstPage.size < MoneyRepository.DEFAULT_PAGE_SIZE
                currentPage += 1
                _state.update { state ->
                    state.copy(
                        currencyConfig = currencyConfig,
                        transactions = firstPage,
                        isLoading = false,
                        endReached = endReached,
                    )
                }
            }.onFailure { throwable ->
                val error = MoneyFlowError.GetAllTransaction(throwable)
                val uiError = errorMapper.getUIErrorMessage(error)
                _state.update { state ->
                    state.copy(isLoading = false, error = uiError)
                }
            }
        }
    }

    fun mapErrorToErrorMessage(error: MoneyFlowError): UIErrorMessage {
        return errorMapper.getUIErrorMessage(error)
    }

    fun loadNextPage(reset: Boolean = false) {
        if (_state.value.isLoadingMore || _state.value.endReached || _state.value.currencyConfig.code.isEmpty()) return
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

internal data class AllTransactionsUiState(
    val transactions: List<MoneyTransaction> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: UIErrorMessage? = null,
    val endReached: Boolean = false,
    val currencyConfig: CurrencyConfig,
)
