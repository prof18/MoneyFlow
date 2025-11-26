package com.prof18.moneyflow

import android.app.Application
import android.content.Context
import com.prof18.moneyflow.di.initKoin
import org.koin.dsl.module

class MoneyFlowApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // TODO: do proper logging setup like FeedFlow
//        Logger.setMinSeverity(if (BuildConfig.DEBUG) Severity.Verbose else Severity.Warn)

        initKoin(
            listOf(
                module {
                    single<Context> { this@MoneyFlowApp }
                },
            ),
        )
    }
}
