package com.prof18.moneyflow.di

import com.prof18.moneyflow.database.DBImportExport
import com.prof18.moneyflow.database.DatabaseHelper
import com.prof18.moneyflow.database.createDatabaseDriver
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {

    single { createDatabaseDriver(get()) }

    single { DatabaseHelper(get(), Dispatchers.Default) }

    single {
        val factory: Settings.Factory = SharedPreferencesSettings.Factory(get())
        factory.create()
    }

    factory { DBImportExport(get(), get()) }
}
