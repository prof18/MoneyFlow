package di

import com.prof18.moneyflow.db.MoneyFlowDB
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import database.DatabaseHelper
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual val platformModule: Module = module {

//    // To close the database at runtime
//    scope(named<SqlDriver>()) {
//        scoped<SqlDriver> {
//            AndroidSqliteDriver(
//                MoneyFlowDB.Schema,
//                get(),
//                "MoneyFlowDB"
//            )
//        }
//    }
//
    single {
        DatabaseHelper.setupDatabase()
       DatabaseHelper.instance
    }
//
}

