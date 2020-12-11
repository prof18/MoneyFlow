package com.prof18.moneyflow.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.gesture.longPressGestureFilter
import androidx.compose.ui.res.vectorResource
import com.prof18.moneyflow.features.categories.data.mapToAndroidIcon
import com.prof18.moneyflow.ui.style.AppMargins
import com.prof18.moneyflow.ui.style.containerColor
import domain.entities.MoneyTransaction

@Composable
fun TransactionCard(
    transaction: MoneyTransaction,
    onLongPress: () -> Unit,
    onClick: () -> Unit

) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                onClick()
            })
            .longPressGestureFilter {
                onLongPress()
            }
    ) {

        Row {

            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(
                        AppMargins.regular,
                    )
                    .background(
                        containerColor(),
                        shape = RoundedCornerShape(AppMargins.regularCornerRadius)
                    )
            ) {
                Icon(
                    vectorResource(id = transaction.icon.mapToAndroidIcon()),
                    modifier = Modifier.padding(AppMargins.small)
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
            text = "${transaction.amount} €",
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(
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