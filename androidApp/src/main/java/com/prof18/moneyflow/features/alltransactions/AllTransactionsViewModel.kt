package com.prof18.moneyflow.features.alltransactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.prof18.moneyflow.domain.entities.MoneyTransaction
import com.prof18.moneyflow.domain.repository.MoneyRepository
import com.prof18.moneyflow.presentation.alltransactions.AllTransactionsUseCase
import kotlinx.coroutines.flow.Flow

class AllTransactionsViewModel(
    private val allTransactionsUseCase: AllTransactionsUseCase
) : ViewModel() {

    val transactionPagingFlow: Flow<PagingData<MoneyTransaction>> = Pager(
        PagingConfig(pageSize = MoneyRepository.DEFAULT_PAGE_SIZE.toInt())
    ) {
        TransactionPagingSource(allTransactionsUseCase)
    }.flow.cachedIn(viewModelScope)
}