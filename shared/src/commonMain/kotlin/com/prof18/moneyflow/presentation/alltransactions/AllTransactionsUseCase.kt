package com.prof18.moneyflow.presentation.alltransactions

import com.prof18.moneyflow.domain.entities.MoneyTransaction
import com.prof18.moneyflow.domain.repository.MoneyRepository
import kotlin.native.HiddenFromObjC
import kotlin.native.ObjCName

@ObjCName("_AllTransactionsUseCase")
class AllTransactionsUseCase(
    private val moneyRepository: MoneyRepository,
) {

    @HiddenFromObjC
    suspend fun getTransactionsPaginated(
        pageNum: Long,
        pageSize: Long,
    ): List<MoneyTransaction> {
        return moneyRepository.getTransactionsPaginated(pageNum, pageSize)
    }
}
