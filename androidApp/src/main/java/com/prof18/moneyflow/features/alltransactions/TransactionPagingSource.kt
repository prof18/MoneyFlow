package com.prof18.moneyflow.features.alltransactions

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.prof18.moneyflow.domain.entities.MoneyFlowError
import com.prof18.moneyflow.domain.entities.MoneyTransaction
import com.prof18.moneyflow.domain.repository.MoneyRepository
import com.prof18.moneyflow.presentation.alltransactions.AllTransactionsUseCase
import com.prof18.moneyflow.utils.logError

internal class TransactionPagingSource(
    private val allTransactionsUseCase: AllTransactionsUseCase,
) : PagingSource<Long, MoneyTransaction>() {

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, MoneyTransaction> {
        val pageNum = params.key ?: 0
        return try {
            val data = allTransactionsUseCase.getTransactionsPaginated(
                pageNum = pageNum,
                pageSize = MoneyRepository.DEFAULT_PAGE_SIZE,
            )
            val nextKey = data.lastOrNull()?.milliseconds
            LoadResult.Page(
                data = data,
                prevKey = null, // Only paging forward.
                nextKey = nextKey,
            )
        } catch (throwable: Throwable) {
            val error = MoneyFlowError.GetAllTransaction(throwable)
            throwable.logError(error)
            val paginationError = PaginationError(error)
            LoadResult.Error(paginationError)
        }
    }

    override fun getRefreshKey(state: PagingState<Long, MoneyTransaction>): Long? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
