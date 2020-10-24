package data.db

import asFlow
import com.prof18.moneyflow.db.MoneyFlowDB
import com.prof18.moneyflow.db.Transactions
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import mapToList

// TODO: will change to internal

class DatabaseSource (
    private val sqlDriver: SqlDriver,
    private val backgroundDispatcher: CoroutineDispatcher
) {

    private var dbRef: MoneyFlowDB = createQueryWrapper(sqlDriver)

    fun selectAllTransaction(): Flow<List<Transactions>> =
        dbRef.transactionQueries
            .selectAll()
            .asFlow()
            .mapToList()
            .flowOn(backgroundDispatcher)

    suspend fun insertTransaction(transaction: Transactions) {
//        log.d { "Inserting ${breedNames.size} breeds into database" }
//        dbRef.transactionWithContext(backgroundDispatcher) {
//            breedNames.forEach { name ->
//                dbRef.tableQueries.insertBreed(null, name, 0)
//            }
//        }
    }


    /*

    For pagination:
        suspend fun selectById(id: Long): Flow<List<Breed>> =
        dbRef.tableQueries
            .selectById(id)
            .asFlow()
            .mapToList()
            .flowOn(backgroundDispatcher)

     */

    // TODO: delete and change to flow
//    fun getData(): List<Breed> {
//        return dbRef.tableQueries.selectAll().executeAsList()
//    }


    fun close() {
        sqlDriver.close()
    }
}