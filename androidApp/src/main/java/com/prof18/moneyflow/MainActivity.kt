package com.prof18.moneyflow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.ui.platform.setContent
import com.prof18.moneyflow.style.MoneyFlowTheme
import com.prof18.moneyflow.ui.HomeViewModel
import com.prof18.moneyflow.ui.home.HomeScreen
import di.recreateDatabaseScope
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    val viewModel: HomeViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MoneyFlowTheme {
//                Text("aegae")
                HomeScreen(viewModel)
            }
        }

    }
}
