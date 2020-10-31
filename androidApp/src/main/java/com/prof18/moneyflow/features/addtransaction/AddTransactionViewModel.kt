package com.prof18.moneyflow.features.addtransaction

import InsertTransactionDTO
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prof18.moneyflow.R
import com.prof18.moneyflow.features.addtransaction.data.TransactionTypeRadioItem
import data.db.model.TransactionType
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.getKoin
import presentation.addtransaction.AddTransactionUseCase
import java.text.SimpleDateFormat
import java.util.*

class AddTransactionViewModel(
    private val addTransactionUseCase: AddTransactionUseCase
) : ViewModel() {

    // States
    var selectedTransactionType: TransactionTypeRadioItem by mutableStateOf(
        TransactionTypeRadioItem(
            R.string.transaction_type_outcome,
            TransactionType.OUTCOME
        )
    )
    var amountText: String by mutableStateOf("")
    var descriptionText: String by mutableStateOf("")
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

        val amount = amountText.toDoubleOrNull()
        if (amount == null) {
            // TODO: show error
            return
        }

       viewModelScope.launch {
           try {
               addTransactionUseCase.insertTransaction(
                   InsertTransactionDTO(
                       dateMillis = selectedDate.timeInMillis,
                       amount = amount,
                       description = descriptionText,
                       categoryId = categoryId,
                       transactionType = selectedTransactionType.transactionType
                   )
               )
           } catch (e: Exception) {
               e.printStackTrace()
               // TODO: show something in UI
           }
       }
    }
}

class AddTransactionViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddTransactionViewModel::class.java)) {
            return AddTransactionViewModel(getKoin().get()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}