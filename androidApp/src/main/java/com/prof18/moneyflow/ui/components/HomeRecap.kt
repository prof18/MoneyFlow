package com.prof18.moneyflow.ui.components

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.ui.tooling.preview.Preview
import com.prof18.moneyflow.style.AppMargins
import com.prof18.moneyflow.style.MoneyFlowTheme

@Composable
fun HomeRecap() {

    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(AppMargins.regular)
    ) {

        Text(
            text = "Total Balance",
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.gravity(Alignment.CenterHorizontally),
        )

        Row(
            modifier = Modifier.gravity(Alignment.CenterHorizontally),
        ) {
            Text(
                text = "€",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.gravity(Alignment.CenterVertically),
            )

            Spacer(Modifier.preferredWidth(AppMargins.small))

            Text(
                text = "1230",
                style = MaterialTheme.typography.h3
            )
        }

        Spacer(Modifier.preferredHeight(AppMargins.medium))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column() {
                Text(text = "-350 €", style = MaterialTheme.typography.h5)
                Text(text = "Expense", style = MaterialTheme.typography.subtitle2)
            }
            Column() {
                Text(text = "+1300 €", style = MaterialTheme.typography.h5)
                Text(
                    text = "Income",
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.gravity(Alignment.End),
                )
            }
        }

    }

}


@Preview
@Composable
fun HomeRecapPreview() {
    MoneyFlowTheme {
        HomeRecap()
    }
}

@Preview
@Composable
fun HomeRecapDarkPreview() {
    MoneyFlowTheme(darkTheme = true) {
        HomeRecap()
    }
}