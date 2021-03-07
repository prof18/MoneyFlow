package com.prof18.moneyflow.features.home.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prof18.moneyflow.R
import com.prof18.moneyflow.ui.style.*
import com.prof18.moneyflow.domain.entities.BalanceRecap

@Composable
fun HomeRecap(
    balanceRecap: BalanceRecap
) {

    // TODO: fix string handling

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppMargins.regular)
    ) {




        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
        ) {
            Text(
                text = "€",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.align(Alignment.CenterVertically),
            )

            Spacer(Modifier.width(AppMargins.small))

            Text(
                text = balanceRecap.totalBalance.toString(),
                style = MaterialTheme.typography.h3
            )
        }

        Text(
            text = "Total Balance",
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )



        Spacer(Modifier.height(AppMargins.medium))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {

            Row {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = AppMargins.regular)
                        .background(upArrowCircleColor(), shape = CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_up_rotate),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(AppMargins.small)
                            .size(24.dp),
                        tint = upArrowColor()
                    )
                }

                Column() {
                    Text(
                        text = "+${balanceRecap.monthlyIncome} €",
                        style = MaterialTheme.typography.h5
                    )
                    Text(
                        text = "Income",
                        style = MaterialTheme.typography.subtitle2,
                    )
                }


            }

            Row {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = AppMargins.regular, start = AppMargins.medium)
                        .background(downArrowCircleColor(), shape = CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_down_rotate),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(AppMargins.small)
                            .size(24.dp),
                        tint = downArrowColor()
                    )
                }

                Column() {
                    Text(
                        text = "-${balanceRecap.monthlyExpenses} €",
                        style = MaterialTheme.typography.h5
                    )
                    Text(
                        text = "Expense",
                        style = MaterialTheme.typography.subtitle2,
                        modifier = Modifier.align(Alignment.End),
                    )
                }


            }


        }

    }

}


@Preview
@Composable
fun HomeRecapPreview() {
    MoneyFlowTheme {
        HomeRecap(
            balanceRecap = BalanceRecap(
                totalBalance = 1200.0,
                monthlyIncome = 150.0,
                monthlyExpenses = 200.0
            )
        )
    }
}

//@Preview
//@Composable
//fun HomeRecapDarkPreview() {
//    MoneyFlowTheme(darkTheme = true) {
//        HomeRecap()
//    }
//}