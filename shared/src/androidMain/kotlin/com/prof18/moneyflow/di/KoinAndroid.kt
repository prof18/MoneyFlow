package com.prof18.moneyflow.di

import com.prof18.moneyflow.database.DatabaseHelper
import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.Settings
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {

    single {
        DatabaseHelper.setupDatabase()
       DatabaseHelper.instance
    }

    single {
        val factory: Settings.Factory = AndroidSettings.Factory(get())
       factory.create()
    }

//
}

