package com.prof18.moneyflow.features.addtransaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prof18.moneyflow.data.MoneyRepository
import com.prof18.moneyflow.database.model.TransactionType
import com.prof18.moneyflow.domain.entities.CurrencyConfig
import com.prof18.moneyflow.domain.entities.MoneyFlowError
import com.prof18.moneyflow.domain.entities.MoneyFlowResult
import com.prof18.moneyflow.presentation.MoneyFlowErrorMapper
import com.prof18.moneyflow.presentation.addtransaction.AddTransactionAction
import com.prof18.moneyflow.presentation.addtransaction.TransactionToSave
import com.prof18.moneyflow.presentation.model.UIErrorMessage
import com.prof18.moneyflow.utils.formatDateDayMonthYear
import com.prof18.moneyflow.utils.logError
import com.prof18.moneyflow.utils.toAmountCents
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import money_flow.shared.generated.resources.Res
import money_flow.shared.generated.resources.amount_not_empty_error
import kotlin.time.Clock

internal class AddTransactionViewModel(
    private val moneyRepository: MoneyRepository,
    private val errorMapper: MoneyFlowErrorMapper,
) : ViewModel() {

    private val initialSelectedDateMillis: Long = Clock.System.now().toEpochMilliseconds()

    private val _uiState = MutableStateFlow(
        AddTransactionUiState(
            selectedTransactionType = TransactionType.INCOME,
            amountText = "",
            descriptionText = null,
            dateLabel = initialSelectedDateMillis.formatDateDayMonthYear(),
            addTransactionAction = null,
            currencyConfig = null,
            selectedDateMillis = initialSelectedDateMillis,
        ),
    )
    val uiState: StateFlow<AddTransactionUiState> = _uiState

    init {
        observeCurrencyConfig()
    }

    private fun observeCurrencyConfig() {
        viewModelScope.launch {
            moneyRepository.getCurrencyConfig().collectLatest { config ->
                _uiState.update { state ->
                    state.copy(currencyConfig = config)
                }
            }
        }
    }

    fun updateSelectedDate(selectedDateMillis: Long) {
        _uiState.update { state ->
            state.copy(
                dateLabel = selectedDateMillis.formatDateDayMonthYear(),
                selectedDateMillis = selectedDateMillis,
            )
        }
    }

    fun addTransaction(categoryId: Long) {
        val currencyConfig = uiState.value.currencyConfig ?: CurrencyConfig("EUR", "€", 2)
        val amountCents = uiState.value.amountText.toAmountCents(currencyConfig)
        if (amountCents == null) {
            val errorMessage = UIErrorMessage(
                message = Res.string.amount_not_empty_error,
            )
            _uiState.update { state ->
                state.copy(addTransactionAction = AddTransactionAction.ShowError(errorMessage))
            }
            return
        }

        viewModelScope.launch {
            val result = try {
                moneyRepository.insertTransaction(
                    TransactionToSave(
                        dateMillis = uiState.value.selectedDateMillis,
                        amountCents = amountCents,
                        description = uiState.value.descriptionText,
                        categoryId = categoryId,
                        transactionType = uiState.value.selectedTransactionType,
                    ),
                )
                MoneyFlowResult.Success(Unit)
            } catch (throwable: Throwable) {
                val error = MoneyFlowError.AddTransaction(throwable)
                throwable.logError(error)
                MoneyFlowResult.Error(errorMapper.getUIErrorMessage(error))
            }
            _uiState.update { state ->
                val action = when (result) {
                    is MoneyFlowResult.Success -> AddTransactionAction.GoBack
                    is MoneyFlowResult.Error -> AddTransactionAction.ShowError(result.uiErrorMessage)
                }
                state.copy(addTransactionAction = action)
            }
        }
    }

    fun resetAction() {
        _uiState.update { state ->
            state.copy(addTransactionAction = null)
        }
    }

    fun updateAmountText(amountText: String) {
        _uiState.update { state ->
            state.copy(amountText = amountText)
        }
    }

    fun updateDescriptionText(description: String?) {
        _uiState.update { state ->
            state.copy(descriptionText = description)
        }
    }

    fun updateTransactionType(transactionType: TransactionType) {
        _uiState.update { state ->
            state.copy(selectedTransactionType = transactionType)
        }
    }
}

internal data class AddTransactionUiState(
    val selectedTransactionType: TransactionType,
    val amountText: String,
    val descriptionText: String?,
    val dateLabel: String?,
    val addTransactionAction: AddTransactionAction?,
    val currencyConfig: CurrencyConfig?,
    val selectedDateMillis: Long,
)
