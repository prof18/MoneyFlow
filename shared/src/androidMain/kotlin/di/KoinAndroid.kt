package di

import com.prof18.moneyflow.db.MoneyFlowDB
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import database.DatabaseHelper
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {

    // TODO: add platform specific stuff

    single<SqlDriver> {
        val s = AndroidSqliteDriver(
            MoneyFlowDB.Schema,
            get(),
            "MoneyFlowDB"
        )



        return@single s
    }



//    single<SqlDriver> {
//        AndroidSqliteDriver(
//            com.prof18.moneyflow.db.MoneyFlowDB,
//            get(),
//            "KampkitDb"
//        )
//    }

//    single<Settings> {
//        AndroidSettings(get())
//    }


}