package com.prof18.moneyflow.presentation.alltransactions

import com.prof18.moneyflow.domain.entities.MoneyTransaction
import com.prof18.moneyflow.domain.repository.MoneyRepository
import kotlin.native.HiddenFromObjC

@HiddenFromObjC
class AllTransactionsUseCase(
    private val moneyRepository: MoneyRepository,
) {
    suspend fun getTransactionsPaginated(
        pageNum: Long,
        pageSize: Long,
    ): List<MoneyTransaction> {
        return moneyRepository.getTransactionsPaginated(pageNum, pageSize)
    }
}
