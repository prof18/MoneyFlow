package com.prof18.moneyflow.features.addtransaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prof18.moneyflow.data.db.model.TransactionType
import com.prof18.moneyflow.domain.entities.MoneyFlowError
import com.prof18.moneyflow.domain.entities.MoneyFlowResult
import com.prof18.moneyflow.domain.repository.MoneyRepository
import com.prof18.moneyflow.platform.LocalizedStringProvider
import com.prof18.moneyflow.presentation.MoneyFlowErrorMapper
import com.prof18.moneyflow.presentation.addtransaction.AddTransactionAction
import com.prof18.moneyflow.presentation.addtransaction.TransactionToSave
import com.prof18.moneyflow.presentation.model.UIErrorMessage
import com.prof18.moneyflow.utils.formatDateDayMonthYear
import com.prof18.moneyflow.utils.logError
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.LocalDate

class AddTransactionViewModel(
    private val moneyRepository: MoneyRepository,
    private val errorMapper: MoneyFlowErrorMapper,
    private val localizedStringProvider: LocalizedStringProvider,
) : ViewModel() {

    // States
    var selectedTransactionType: TransactionType by mutableStateOf(TransactionType.INCOME)
    var amountText: String by mutableStateOf("")
    var descriptionText: String? by mutableStateOf(null)
    var dateLabel: String? by mutableStateOf(null)
    var addTransactionAction: AddTransactionAction? by mutableStateOf(null)
        private set

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
        dateLabel = selectedDateMillis.formatDateDayMonthYear()
    }

    fun addTransaction(categoryId: Long) {
        val amount = amountText.toDoubleOrNull()
        if (amount == null) {
            val errorMessage = UIErrorMessage(
                message = localizedStringProvider.get("amount_not_empty_error"),
                nerdMessage = "",
            )
            addTransactionAction = AddTransactionAction.ShowError(errorMessage)
            return
        }

        viewModelScope.launch {
            val result = try {
                moneyRepository.insertTransaction(
                    TransactionToSave(
                        dateMillis = selectedDateMillis,
                        amount = amount,
                        description = descriptionText,
                        categoryId = categoryId,
                        transactionType = selectedTransactionType,
                    ),
                )
                MoneyFlowResult.Success(Unit)
            } catch (throwable: Throwable) {
                val error = MoneyFlowError.AddTransaction(throwable)
                throwable.logError(error)
                MoneyFlowResult.Error(errorMapper.getUIErrorMessage(error))
            }
            addTransactionAction = when (result) {
                is MoneyFlowResult.Success -> {
                    AddTransactionAction.GoBack
                }
                is MoneyFlowResult.Error -> {
                    AddTransactionAction.ShowError(result.uiErrorMessage)
                }
            }
        }
    }

    fun resetAction() {
        addTransactionAction = null
    }

    private fun currentLocalDate(): LocalDate =
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
}
