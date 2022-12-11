package com.prof18.moneyflow.di

import com.prof18.moneyflow.database.DBImportExport
import com.prof18.moneyflow.database.DBImportExportImpl
import com.prof18.moneyflow.database.DatabaseHelper
import com.prof18.moneyflow.dropbox.DropboxDataSource
import com.prof18.moneyflow.dropbox.DropboxDataSourceAndroid
import com.prof18.moneyflow.settings.EncryptedSettingsFactory
import com.russhwolf.settings.Settings
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {

    single {
        DatabaseHelper.setupDatabase()
        DatabaseHelper.instance
    }

    single {
        val factory: Settings.Factory = EncryptedSettingsFactory(get())
        factory.create()
    }

    factory<DBImportExport> { DBImportExportImpl(get(), get()) }

    single<DropboxDataSource> { DropboxDataSourceAndroid() }
}
