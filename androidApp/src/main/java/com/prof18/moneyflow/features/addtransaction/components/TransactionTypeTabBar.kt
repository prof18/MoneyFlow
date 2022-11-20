package com.prof18.moneyflow.features.addtransaction.components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prof18.moneyflow.R
import com.prof18.moneyflow.data.db.model.TransactionType
import com.prof18.moneyflow.ui.components.ArrowCircleIcon
import com.prof18.moneyflow.ui.style.Margins
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import com.prof18.moneyflow.ui.style.downArrowCircleColor
import com.prof18.moneyflow.ui.style.downArrowColor
import com.prof18.moneyflow.ui.style.upArrowCircleColor
import com.prof18.moneyflow.ui.style.upArrowColor

@Composable
internal fun TransactionTypeTabBar(
    transactionType: TransactionType,
    onTabSelected: (tabPage: TransactionType) -> Unit,
    modifier: Modifier = Modifier,
) {
    TabRow(
        modifier = modifier,
        selectedTabIndex = transactionType.ordinal,
        backgroundColor = Color.Transparent,
        indicator = { tabPositions ->
            TransactionTabIndicator(tabPositions, transactionType)
        },
        divider = { },
    ) {
        TransactionTab(
            boxColor = upArrowCircleColor(),
            arrowColor = upArrowColor(),
            iconId = R.drawable.ic_arrow_up_rotate,
            title = stringResource(id = R.string.transaction_type_income),
            onClick = { onTabSelected(TransactionType.INCOME) },
        )
        TransactionTab(
            boxColor = downArrowCircleColor(),
            arrowColor = downArrowColor(),
            iconId = R.drawable.ic_arrow_down_rotate,
            title = stringResource(id = R.string.transaction_type_outcome),
            onClick = { onTabSelected(TransactionType.OUTCOME) },
        )
    }
}

@Composable
private fun TransactionTab(
    boxColor: Color,
    arrowColor: Color,
    @DrawableRes iconId: Int,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ArrowCircleIcon(
            boxColor = boxColor,
            iconID = iconId,
            arrowColor = arrowColor,
            iconSize = 18.dp,
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title)
    }
}

@Composable
private fun TransactionTabIndicator(
    tabPositions: List<TabPosition>,
    transactionType: TransactionType,
) {
    val transition = updateTransition(transactionType, label = "tab_selection_transition")
    val indicatorLeft by transition.animateDp(label = "indicator_left_animation") { page ->
        tabPositions[page.ordinal].left
    }
    val indicatorRight by transition.animateDp(label = "indicator_right_animation") { page ->
        tabPositions[page.ordinal].right
    }

    Box(
        Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = indicatorLeft)
            .width(indicatorRight - indicatorLeft)
            .fillMaxSize()
            .border(
                BorderStroke(1.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.3f)),
                RoundedCornerShape(4.dp),
            ),
    )
}

@Preview(name = "TransactionTypeTabBarPreview Light")
@Preview(name = "TransactionTypeTabBarPreview Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TransactionTypeTabBarPreview() {
    MoneyFlowTheme {
        Surface {
            TransactionTypeTabBar(
                transactionType = TransactionType.INCOME,
                onTabSelected = {},
                modifier = Modifier.padding(Margins.small),
            )
        }
    }
}
