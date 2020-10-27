package com.prof18.moneyflow.ui.components

import androidx.compose.foundation.Box
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import com.prof18.moneyflow.R
import com.prof18.moneyflow.style.AppColors
import com.prof18.moneyflow.style.AppMargins
import domain.model.MoneyTransaction

@Composable
fun TransactionCard(
    transaction: MoneyTransaction
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.clickable(onClick = {
            // TODO
        })
    ) {

        Row(modifier = Modifier.weight(8f)) {

            Box(
                modifier = Modifier
                    .gravity(Alignment.CenterVertically)
                    .padding(
                        AppMargins.regular,
                    ),
                // TODO: find a better color for the dark mode
                backgroundColor = AppColors.darkGrey,
                shape = RoundedCornerShape(AppMargins.regularCornerRadius),
            ) {
                Icon(
                    asset = vectorResource(id = R.drawable.ic_hamburger_solid),
                    modifier = Modifier.gravity(Alignment.CenterVertically)
                        .padding(AppMargins.small)
                )
            }

            Column(
                modifier = Modifier.align(Alignment.CenterVertically).padding(
                    top = AppMargins.regular,
                    bottom = AppMargins.regular,
                    end = AppMargins.regular,
                ),
            ) {

                Text(
                    text = transaction.title,
                    style = MaterialTheme.typography.subtitle1
                )

                Text(
                    text = transaction.formattedDate,
                    style = MaterialTheme.typography.caption
                )
            }
        }

        Text(
            // TODO: fix this
            text = "-${transaction.amount} €",
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .weight(2f)
                .gravity(Alignment.CenterVertically).padding(
                    top = AppMargins.regular,
                    bottom = AppMargins.regular,
                    end = AppMargins.regular,
                )
        )
    }


}

// TODO: restore preview
//@Preview
//@Composable
//fun TransactionPreview() {
//    MoneyFlowTheme {
//        TransactionCard()
//    }
//}
//
//@Preview
//@Composable
//fun TransactionDarkPreview() {
//    MoneyFlowTheme(darkTheme = true) {
//        TransactionCard()
//    }
//}