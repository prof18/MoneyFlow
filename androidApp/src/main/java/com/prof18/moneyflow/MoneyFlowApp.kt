package com.prof18.moneyflow

import android.app.Application
import android.content.Context
import com.prof18.moneyflow.utils.DatabaseImportExport
import di.initKoin
import org.koin.dsl.module
import timber.log.Timber

class MoneyFlowApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        initKoin(
            module {
                single<Context> { this@MoneyFlowApp }
                single { DatabaseImportExport(applicationContext) }
                // We can use it when the koin compose artifact will be available also to the kmp artifacts
//                viewModel {
//                    HomeViewModel(get())
//                }
            },
        )
    }
}