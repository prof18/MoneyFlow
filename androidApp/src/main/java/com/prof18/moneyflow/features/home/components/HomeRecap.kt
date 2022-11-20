package com.prof18.moneyflow.features.home.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prof18.moneyflow.R
import com.prof18.moneyflow.domain.entities.BalanceRecap
import com.prof18.moneyflow.ui.components.HideableTextField
import com.prof18.moneyflow.ui.style.Margins
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import com.prof18.moneyflow.ui.style.downArrowCircleColor
import com.prof18.moneyflow.ui.style.downArrowColor
import com.prof18.moneyflow.ui.style.upArrowCircleColor
import com.prof18.moneyflow.ui.style.upArrowColor

@Composable
@Suppress("LongMethod") // TODO: reduce method length
internal fun HomeRecap(
    balanceRecap: BalanceRecap,
    hideSensitiveData: Boolean,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Margins.regular),
    ) {

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
        ) {
            Text(
                // TODO: inject the currency the user has chosen from somewhere
                text = stringResource(R.string.euro_symbol),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.align(Alignment.CenterVertically),
            )

            Spacer(Modifier.width(Margins.small))

            HideableTextField(
                text = balanceRecap.totalBalance.toString(),
                hide = hideSensitiveData,
                style = MaterialTheme.typography.h3,
            )
        }

        Text(
            text = stringResource(R.string.total_balance),
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )

        Spacer(Modifier.height(Margins.medium))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {

            Row {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = Margins.regular)
                        .background(upArrowCircleColor(), shape = CircleShape),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_up_rotate),
                        contentDescription = stringResource(R.string.up_arrow_content_desc),
                        modifier = Modifier
                            .padding(Margins.small)
                            .size(24.dp),
                        tint = upArrowColor(),
                    )
                }

                Column {
                    HideableTextField(
                        text = "${balanceRecap.monthlyIncome} ${stringResource(id = R.string.euro_symbol)}",
                        hide = hideSensitiveData,
                        style = MaterialTheme.typography.h5,
                    )
                    Text(
                        text = stringResource(id = R.string.transaction_type_income),
                        style = MaterialTheme.typography.subtitle2,
                    )
                }
            }

            Row {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = Margins.regular, start = Margins.medium)
                        .background(downArrowCircleColor(), shape = CircleShape),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_down_rotate),
                        contentDescription = stringResource(R.string.down_arrow_content_desc),
                        modifier = Modifier
                            .padding(Margins.small)
                            .size(24.dp),
                        tint = downArrowColor(),
                    )
                }

                Column {
                    HideableTextField(
                        // TODO: inject the currency the user has chosen from somewhere
                        text = "${balanceRecap.monthlyExpenses} €",
                        hide = hideSensitiveData,
                        style = MaterialTheme.typography.h5,
                    )
                    Text(
                        text = stringResource(id = R.string.transaction_type_outcome),
                        style = MaterialTheme.typography.subtitle2,
                        modifier = Modifier.align(Alignment.End),
                    )
                }
            }
        }
    }
}

@Preview(name = "HomeRecap Light")
@Preview(name = "HomeRecap Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeRecapPreview() {
    MoneyFlowTheme {
        Surface {
            HomeRecap(
                balanceRecap = BalanceRecap(
                    totalBalance = 1200.0,
                    monthlyIncome = 150.0,
                    monthlyExpenses = 200.0,
                ),
                hideSensitiveData = true,
            )
        }
    }
}
