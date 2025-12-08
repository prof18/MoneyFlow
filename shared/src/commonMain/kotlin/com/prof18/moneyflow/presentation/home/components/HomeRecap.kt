package com.prof18.moneyflow.presentation.home.components

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prof18.moneyflow.domain.entities.BalanceRecap
import com.prof18.moneyflow.ui.components.HideableTextField
import com.prof18.moneyflow.ui.style.Margins
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import com.prof18.moneyflow.ui.style.downArrowCircleColor
import com.prof18.moneyflow.ui.style.downArrowColor
import com.prof18.moneyflow.ui.style.upArrowCircleColor
import com.prof18.moneyflow.ui.style.upArrowColor
import money_flow.shared.generated.resources.Res
import money_flow.shared.generated.resources.down_arrow_content_desc
import money_flow.shared.generated.resources.euro_symbol
import money_flow.shared.generated.resources.ic_arrow_down_rotate
import money_flow.shared.generated.resources.ic_arrow_up_rotate
import money_flow.shared.generated.resources.total_balance
import money_flow.shared.generated.resources.transaction_type_income
import money_flow.shared.generated.resources.transaction_type_outcome
import money_flow.shared.generated.resources.up_arrow_content_desc
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

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
                text = stringResource(Res.string.euro_symbol),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.CenterVertically),
            )

            Spacer(Modifier.width(Margins.small))

            HideableTextField(
                text = balanceRecap.totalBalance.toString(),
                hide = hideSensitiveData,
                style = MaterialTheme.typography.displaySmall,
            )
        }

        Text(
            text = stringResource(Res.string.total_balance),
            style = MaterialTheme.typography.titleSmall,
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
                        painter = painterResource(Res.drawable.ic_arrow_up_rotate),
                        contentDescription = stringResource(Res.string.up_arrow_content_desc),
                        modifier = Modifier
                            .padding(Margins.small)
                            .size(24.dp),
                        tint = upArrowColor(),
                    )
                }

                Column {
                    HideableTextField(
                        text = "${balanceRecap.monthlyIncome} ${stringResource(Res.string.euro_symbol)}",
                        hide = hideSensitiveData,
                        style = MaterialTheme.typography.headlineMedium,
                    )
                    Text(
                        text = stringResource(Res.string.transaction_type_income),
                        style = MaterialTheme.typography.titleSmall,
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
                        painter = painterResource(Res.drawable.ic_arrow_down_rotate),
                        contentDescription = stringResource(Res.string.down_arrow_content_desc),
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
                        style = MaterialTheme.typography.headlineMedium,
                    )
                    Text(
                        text = stringResource(Res.string.transaction_type_outcome),
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.align(Alignment.End),
                    )
                }
            }
        }
    }
}

@Preview(name = "HomeRecap Light")
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
