package com.prof18.moneyflow.ui.components

import androidx.compose.foundation.Box
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.prof18.moneyflow.R
import com.prof18.moneyflow.style.AppColors
import com.prof18.moneyflow.style.AppMargins
import com.prof18.moneyflow.style.MoneyFlowTheme
import com.prof18.moneyflow.style.MoneyFlowTypography


// TODO: pass real data

@Composable
fun TransactionCard(

) {

    Card(
        backgroundColor = AppColors.lightGrey,
        elevation = 8.dp,
        modifier = Modifier.clickable(
            onClick = {

                // TODO: handle click

            },
        ),
    ) {

        Row {
            Box(
                modifier = Modifier
                    .padding(AppMargins.regular,
                    ),
                backgroundColor = AppColors.darkGrey,
                shape = RoundedCornerShape(8.dp),
            ) {
                Icon(
                    asset = vectorResource(id = R.drawable.ic_hamburger),
                    modifier = Modifier.gravity(Alignment.CenterVertically).padding(AppMargins.small)
                )
            }

            Column(
                modifier = Modifier.gravity(Alignment.CenterVertically).padding(
                    top = AppMargins.regular,
                    bottom = AppMargins.regular,
                    end = AppMargins.regular,
                ),
            ) {

                Text(
                    text = "Dinner with friends",
                    style = MaterialTheme.typography.subtitle1
                )

                Text(
                    text = "21 Jan 2020",
                    style = MaterialTheme.typography.caption
                )
            }

            Text(
                text = "-20 €",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.gravity(Alignment.CenterVertically).padding(
                    top = AppMargins.regular,
                    bottom = AppMargins.regular,
                    end = AppMargins.regular,
                )
            )
        }
    }
}


@Preview
@Composable
fun TransactionPreview() {
    MoneyFlowTheme {
        TransactionCard()
    }
}

@Preview
@Composable
fun TransactionDarkPreview() {
    MoneyFlowTheme(darkTheme = true) {
        TransactionCard()
    }
}