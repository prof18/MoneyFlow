package com.prof18.moneyflow.features.addtransaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prof18.moneyflow.database.model.TransactionType
import com.prof18.moneyflow.domain.entities.MoneyFlowError
import com.prof18.moneyflow.domain.entities.MoneyFlowResult
import com.prof18.moneyflow.data.MoneyRepository
import com.prof18.moneyflow.presentation.MoneyFlowErrorMapper
import com.prof18.moneyflow.presentation.addtransaction.AddTransactionAction
import com.prof18.moneyflow.presentation.addtransaction.TransactionToSave
import com.prof18.moneyflow.presentation.model.UIErrorMessage
import com.prof18.moneyflow.utils.formatDateDayMonthYear
import com.prof18.moneyflow.utils.logError
import money_flow.shared.generated.resources.Res
import money_flow.shared.generated.resources.amount_not_empty_error
import money_flow.shared.generated.resources.error_nerd_message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.LocalDate

class AddTransactionViewModel(
    private val moneyRepository: MoneyRepository,
    private val errorMapper: MoneyFlowErrorMapper,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        AddTransactionUiState(
            selectedTransactionType = TransactionType.INCOME,
            amountText = "",
            descriptionText = null,
            dateLabel = null,
            addTransactionAction = null,
        ),
    )
    val uiState: StateFlow<AddTransactionUiState> = _uiState

    // Private variables
    private var selectedDateMillis: Long = Clock.System.now().toEpochMilliseconds()
    private var yearNumber: Int = currentLocalDate().year
    private var monthNumber: Int = currentLocalDate().monthNumber - 1
    private var dayNumber: Int = currentLocalDate().dayOfMonth

    init {
        updateDateLabel()
    }

    fun setYearNumber(yearNumber: Int) {
        this.yearNumber = yearNumber
    }

    fun setMonthNumber(monthNumber: Int) {
        this.monthNumber = monthNumber - 1
    }

    fun setDayNumber(dayNumber: Int) {
        this.dayNumber = dayNumber
    }

    fun saveDate() {
        val localDate = LocalDate(yearNumber, monthNumber + 1, dayNumber)
        selectedDateMillis = localDate.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
        updateDateLabel()
    }

    private fun updateDateLabel() {
        _uiState.update { state ->
            state.copy(dateLabel = selectedDateMillis.formatDateDayMonthYear())
        }
    }

    fun addTransaction(categoryId: Long) {
        val amount = uiState.value.amountText.toDoubleOrNull()
        if (amount == null) {
            val errorMessage = UIErrorMessage(
                message = Res.string.amount_not_empty_error,
                messageKey = "amount_not_empty_error",
                messageArgs = emptyList(),
                nerdMessage = Res.string.error_nerd_message,
                nerdMessageKey = "error_nerd_message",
                nerdMessageArgs = emptyList(),
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
                        dateMillis = selectedDateMillis,
                        amount = amount,
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

    private fun currentLocalDate(): LocalDate =
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
}

data class AddTransactionUiState(
    val selectedTransactionType: TransactionType,
    val amountText: String,
    val descriptionText: String?,
    val dateLabel: String?,
    val addTransactionAction: AddTransactionAction?,
)
