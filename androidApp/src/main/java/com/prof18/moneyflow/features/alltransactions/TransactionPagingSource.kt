package com.prof18.moneyflow.features.alltransactions

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.prof18.moneyflow.domain.entities.MoneyTransaction
import com.prof18.moneyflow.domain.repository.MoneyRepository
import com.prof18.moneyflow.presentation.alltransactions.AllTransactionsUseCase

class TransactionPagingSource(
    private val allTransactionsUseCase: AllTransactionsUseCase
) : PagingSource<Long, MoneyTransaction>() {

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, MoneyTransaction> {
        try {
            // Start refresh at page 1 if undefined.
            val pageNum = params.key ?: 0
            val response = allTransactionsUseCase.getTransactionsPaginated(
                pageNum = pageNum,
                pageSize = MoneyRepository.DEFAULT_PAGE_SIZE
            )
            val nextKey = response.lastOrNull()?.milliseconds
            return LoadResult.Page(
                data = response,
                prevKey = null, // Only paging forward.
                nextKey = nextKey
            )
        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error if it is an
            // expected error (such as a network failure).
            return LoadResult.Error(
                throwable = e
            )
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