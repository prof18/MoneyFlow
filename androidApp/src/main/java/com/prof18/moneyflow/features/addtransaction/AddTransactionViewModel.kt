package com.prof18.moneyflow.features.addtransaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prof18.moneyflow.R
import com.prof18.moneyflow.features.addtransaction.data.TransactionTypeRadioItem
import data.db.model.TransactionType
import java.text.SimpleDateFormat
import java.util.*

class AddTransactionViewModel : ViewModel() {

    // States
    var selectedTransactionType: TransactionTypeRadioItem by mutableStateOf(
        TransactionTypeRadioItem(
            R.string.transaction_type_outcome,
            TransactionType.OUTCOME
        )
    )
    var amountText: String by mutableStateOf("")
    var dateLabel: String? by mutableStateOf(null)

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
        // TODO: perform adding
    }

}

class AddTransactionViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddTransactionViewModel::class.java)) {
            return AddTransactionViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}