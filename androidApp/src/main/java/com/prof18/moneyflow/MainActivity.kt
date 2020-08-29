package com.prof18.moneyflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.platform.setContent
import com.prof18.moneyflow.style.MoneyFlowTheme
import com.prof18.moneyflow.ui.components.HeaderNavigator
import com.prof18.moneyflow.ui.components.HomeRecap
import com.prof18.moneyflow.ui.components.TransactionCard
import createApplicationScreenMessage

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MoneyFlowTheme {

                Column {
                    HomeRecap()
                    HeaderNavigator()
                    TransactionCard()
                    TransactionCard()
                    TransactionCard()
                }

            }
        }

    }
}
