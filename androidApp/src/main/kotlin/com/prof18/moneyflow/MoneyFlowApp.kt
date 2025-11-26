package com.prof18.moneyflow

import android.app.Application
import android.content.Context
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import com.prof18.moneyflow.androidApp.BuildConfig
import com.prof18.moneyflow.di.initKoin
import org.koin.dsl.module

class MoneyFlowApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Logger.setMinSeverity(if (BuildConfig.DEBUG) Severity.Verbose else Severity.Warn)

        initKoin(
            listOf(
                module {
                    single<Context> { this@MoneyFlowApp }
                }
            )
        )
    }
}
