package com.prof18.moneyflow

import android.app.Application
import android.content.Context
import com.github.aakira.napier.DebugAntilog
import com.github.aakira.napier.Napier
import di.initKoin
import org.koin.dsl.module

class MoneyFlowApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            // init napier
            Napier.base(DebugAntilog())

            /*
            Fabric.with(this, Crashlytics())
            // init napier
            Napier.base(CrashlyticsAntilog(this))
             */
        }

        initKoin(
            module {
                single<Context> { this@MoneyFlowApp }
                // We can use it when the koin compose artifact will be available also to the kmp artifacts
//                viewModel {
//                    HomeViewModel(get())
//                }
            },
        )
    }
}