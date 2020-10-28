package com.prof18.moneyflow

import android.app.Application
import android.content.Context
import di.initKoin
import org.koin.dsl.module

class MoneyFlowApp : Application() {

    override fun onCreate() {
        super.onCreate()

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