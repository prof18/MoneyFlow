package data.db

import asFlow
import com.prof18.moneyflow.db.MoneyFlowDB
import com.prof18.moneyflow.db.TransactionTable
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import mapToList

// TODO: will change to internal

class DatabaseSource (
    // not private and mutable to close and reopen the database at runtime
    var dbRef: MoneyFlowDB,
    private val backgroundDispatcher: CoroutineDispatcher
) {



//    private var dbRef: MoneyFlowDB = createQueryWrapper(sqlDriver)

    fun selectAllTransaction(): Flow<List<TransactionTable>> =
        dbRef.transactionTableQueries
            .selectAll()
            .asFlow()
            .mapToList()
            .flowOn(backgroundDispatcher)

    suspend fun insertTransaction(transaction: TransactionTable) {
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
    fun getData(): List<TransactionTable> {
        return dbRef.transactionTableQueries.selectAll().executeAsList()
    }



}