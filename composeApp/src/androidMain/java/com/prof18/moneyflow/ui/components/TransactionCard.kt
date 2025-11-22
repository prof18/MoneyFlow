package com.prof18.moneyflow.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prof18.moneyflow.R
import com.prof18.moneyflow.domain.entities.MoneyTransaction
import com.prof18.moneyflow.domain.entities.TransactionTypeUI
import com.prof18.moneyflow.features.categories.data.mapToAndroidIcon
import com.prof18.moneyflow.presentation.model.CategoryIcon
import com.prof18.moneyflow.ui.style.Margins
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import com.prof18.moneyflow.ui.style.downArrowCircleColor
import com.prof18.moneyflow.ui.style.downArrowColor
import com.prof18.moneyflow.ui.style.upArrowCircleColor
import com.prof18.moneyflow.ui.style.upArrowColor
import kotlin.math.abs

@Composable
@Suppress("LongMethod") // TODO: reduce method length
internal fun TransactionCard(
    transaction: MoneyTransaction,
    onLongPress: () -> Unit,
    onClick: () -> Unit,
    hideSensitiveData: Boolean,
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
                    },
                )
            },
    ) {

        Row {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(
                        Margins.regular,
                    )
                    .background(
                        MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(Margins.regularCornerRadius),
                    ),
            ) {
                Icon(
                    painter = painterResource(id = transaction.icon.mapToAndroidIcon()),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(Margins.small)
                        .size(28.dp),
                    tint = MaterialTheme.colors.onPrimary,
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(
                        top = Margins.regular,
                        bottom = Margins.regular,
                        end = Margins.regular,
                    ),
            ) {

                Text(
                    text = transaction.title,
                    style = MaterialTheme.typography.subtitle1,
                )

                Text(
                    text = transaction.formattedDate,
                    style = MaterialTheme.typography.caption,
                )
            }
        }

        var boxColor = upArrowCircleColor()
        var arrowColor = upArrowColor()
        var vectorId = R.drawable.ic_arrow_up_rotate

        if (transaction.type == TransactionTypeUI.EXPENSE) {
            boxColor = downArrowCircleColor()
            arrowColor = downArrowColor()
            vectorId = R.drawable.ic_arrow_down_rotate
        }

        Row(
            modifier = Modifier.align(Alignment.CenterVertically),
        ) {

            ArrowCircleIcon(
                boxColor = boxColor,
                iconID = vectorId,
                arrowColor = arrowColor,
                iconSize = 18.dp,
                modifier = Modifier.align(Alignment.CenterVertically),
            )

            HideableTextField(
                // TODO: Inject correct currency
                text = "${abs(transaction.amount)} €",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(Margins.regular),
                hide = hideSensitiveData,
            )
        }
    }
}

@Preview(name = "TransactionCard Light")
@Preview(name = "TransactionCard Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TransactionCardPreview() {
    Surface {
        MoneyFlowTheme {
            TransactionCard(
                transaction = MoneyTransaction(
                    id = 0,
                    title = "Eating out",
                    icon = CategoryIcon.IC_HAMBURGER_SOLID,
                    amount = 30.0,
                    type = TransactionTypeUI.EXPENSE,
                    milliseconds = 0,
                    formattedDate = "12/12/21",
                ),
                onLongPress = {},
                onClick = {},
                hideSensitiveData = true,
            )
        }
    }
}
