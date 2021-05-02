package com.prof18.moneyflow

import android.app.Application
import com.prof18.moneyflow.di.appModule
import com.prof18.moneyflow.di.initKoin
import org.koin.android.ext.koin.androidContext
import timber.log.Timber

class MoneyFlowApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        initKoin {
            androidContext(this@MoneyFlowApp)
            modules(appModule)
        }
    }
}