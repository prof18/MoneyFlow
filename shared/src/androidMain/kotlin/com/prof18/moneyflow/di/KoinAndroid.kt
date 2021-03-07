package com.prof18.moneyflow.di

import com.prof18.moneyflow.database.DatabaseHelper
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {

//    // To close the com.prof18.moneyflow.database at runtime
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

