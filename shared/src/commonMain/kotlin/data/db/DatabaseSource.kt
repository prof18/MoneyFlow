package data.db

import com.prof18.moneyflow.db.Breed
import com.prof18.moneyflow.db.MoneyFlowDB
import com.squareup.sqldelight.db.SqlDriver

// TODO: will change to internal

class DatabaseSource (
    private val sqlDriver: SqlDriver
) {

    private var dbRef: MoneyFlowDB = createQueryWrapper(sqlDriver)


    // TODO: delete and change to flow
    fun getData(): List<Breed> {
        return dbRef.tableQueries.selectAll().executeAsList()
    }


    fun close() {
        sqlDriver.close()
    }
}