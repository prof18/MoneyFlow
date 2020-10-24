package com.prof18.moneyflow

import android.app.Application
import android.content.Context
import android.util.Log
import com.prof18.moneyflow.ui.HomeViewModel
import com.squareup.sqldelight.db.SqlDriver
import data.db.DatabaseSource
import database.DatabaseHelper
import di.initKoin
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class MoneyFlowApp : Application() {

    override fun onCreate() {
        super.onCreate()

//        DatabaseHelper.appContext = applicationContext

        initKoin(
            module {
                single<Context> { this@MoneyFlowApp }
                viewModel {
                    HomeViewModel(get())
                }
            },
        )

    }

}