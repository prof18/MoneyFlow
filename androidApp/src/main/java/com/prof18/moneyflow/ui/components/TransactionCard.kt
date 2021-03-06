package com.prof18.moneyflow.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prof18.moneyflow.R
import com.prof18.moneyflow.features.categories.data.mapToAndroidIcon
import com.prof18.moneyflow.ui.style.AppMargins
import com.prof18.moneyflow.ui.style.LightAppColors
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import domain.entities.MoneyTransaction
import domain.entities.TransactionTypeUI
import presentation.CategoryIcon

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
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        onLongPress()
                    }
                )
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
                        LightAppColors.blue1,
                        shape = RoundedCornerShape(AppMargins.regularCornerRadius)
                    )
            ) {
                Icon(
                    painter = painterResource(id = transaction.icon.mapToAndroidIcon()),
                    contentDescription = null,
                    modifier = Modifier.padding(AppMargins.small),
                    tint = LightAppColors.gray4 // TODO: change
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(
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

        var boxColor = LightAppColors.green3
        var arrowColor = LightAppColors.green1
        var vectorId = R.drawable.ic_arrow_up_rotate

        if (transaction.type == TransactionTypeUI.EXPENSE) {
            boxColor = LightAppColors.red3
            arrowColor = LightAppColors.red1
            vectorId = R.drawable.ic_arrow_down_rotate
        }

        Row(
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {

            ArrowCircleIcon(
                boxColor = boxColor,
                iconID = vectorId,
                arrowColor = arrowColor,
                iconSize = 18.dp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

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


}


// TODO: restore preview
@Preview
@Composable
fun TransactionPreview() {
    MoneyFlowTheme {
        TransactionCard(
            transaction = MoneyTransaction(
                id = 0,
                title = "Eating out",
                icon = CategoryIcon.IC_HAMBURGER_SOLID,
                amount = 30.0,
                type = TransactionTypeUI.EXPENSE,
                formattedDate = "12/12/21"
            ),
            onLongPress = {},
            onClick = {}
        )
    }
}
//
//@Preview
//@Composable
//fun TransactionDarkPreview() {
//    MoneyFlowTheme(darkTheme = true) {
//        TransactionCard()
//    }
//}