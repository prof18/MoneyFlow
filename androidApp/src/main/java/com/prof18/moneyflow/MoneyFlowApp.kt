package com.prof18.moneyflow

import android.app.Application
import android.content.Context
import com.prof18.moneyflow.features.settings.DropboxClient
import com.prof18.moneyflow.features.settings.DropboxLoginViewModel
import com.prof18.moneyflow.utils.DatabaseImportExport
import di.initKoin
import org.koin.androidx.viewmodel.dsl.viewModel
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
                single { DropboxClient(get(), get()) }
                viewModel {
                    // We can use it with Compose ViewModels when the koin compose artifact
                    // will be available also to the kmp artifacts
                    DropboxLoginViewModel(get(), get())
                }
            },
        )
    }
}