package com.prof18.moneyflow

import android.app.Application
import android.content.Context
import com.prof18.moneyflow.di.appModule
import com.prof18.moneyflow.di.initKoin
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
            } + appModule,
        )
    }
}
