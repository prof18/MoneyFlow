package com.prof18.moneyflow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.ui.platform.setContent
import com.prof18.moneyflow.style.MoneyFlowTheme
import com.prof18.moneyflow.ui.components.HeaderNavigator
import com.prof18.moneyflow.ui.components.HomeRecap
import com.prof18.moneyflow.ui.components.TransactionCard

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MoneyFlowTheme {

                Scaffold(
                    bodyContent = {
                        Column {
                            HomeRecap()
                            HeaderNavigator()
                            TransactionCard()
                            TransactionCard()
                            TransactionCard()
                        }
                    },
                )
            }
        }
    }
}
