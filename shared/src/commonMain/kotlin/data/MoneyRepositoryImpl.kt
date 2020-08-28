package data

import domain.model.BalanceRecap
import domain.model.Transaction
import domain.repository.MoneyRepository
import kotlinx.coroutines.flow.Flow

class MoneyRepositoryImpl: MoneyRepository {

    override fun getBalanceRecap(): Flow<BalanceRecap> {
        TODO("Not yet implemented")
    }

    override fun getLatestTransactions(): Flow<List<Transaction>> {
        TODO("Not yet implemented")
    }
}



