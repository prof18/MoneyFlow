package com.prof18.moneyflow.features.addtransaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prof18.moneyflow.data.db.model.TransactionType
import com.prof18.moneyflow.domain.entities.MoneyFlowResult
import com.prof18.moneyflow.platform.LocalizedStringProvider
import com.prof18.moneyflow.presentation.addtransaction.AddTransactionAction
import com.prof18.moneyflow.presentation.addtransaction.AddTransactionUseCase
import com.prof18.moneyflow.presentation.addtransaction.TransactionToSave
import com.prof18.moneyflow.presentation.model.UIErrorMessage
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

internal class AddTransactionViewModel(
    private val addTransactionUseCase: AddTransactionUseCase,
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
    private var yearNumber: Int = Calendar.getInstance().get(Calendar.YEAR)
    private var monthNumber: Int = Calendar.getInstance().get(Calendar.MONTH)
    private var dayNumber = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

    private var selectedDate: Calendar = Calendar.getInstance()
        set(value) {
            val formatter = SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault())
            dateLabel = formatter.format(value.time)
            field = value
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
        val calendar = Calendar.getInstance().apply {
            set(yearNumber, monthNumber, dayNumber)
        }
        selectedDate = calendar
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
            val result = addTransactionUseCase.insertTransaction(
                TransactionToSave(
                    dateMillis = selectedDate.timeInMillis,
                    amount = amount,
                    description = descriptionText,
                    categoryId = categoryId,
                    transactionType = selectedTransactionType,
                ),
            )
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
}
