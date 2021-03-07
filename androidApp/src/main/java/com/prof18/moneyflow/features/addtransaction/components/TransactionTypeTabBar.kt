package com.prof18.moneyflow.features.addtransaction.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.prof18.moneyflow.R
import com.prof18.moneyflow.ui.components.ArrowCircleIcon
import com.prof18.moneyflow.ui.style.downArrowCircleColor
import com.prof18.moneyflow.ui.style.downArrowColor
import com.prof18.moneyflow.ui.style.upArrowCircleColor
import com.prof18.moneyflow.ui.style.upArrowColor
import com.prof18.moneyflow.data.db.model.TransactionType

@Composable
fun TransactionTypeTabBar(
    transactionType: TransactionType,
    onTabSelected: (tabPage: TransactionType) -> Unit,
    modifier: Modifier = Modifier
) {
    TabRow(
        modifier = modifier,
        selectedTabIndex = transactionType.ordinal,
        backgroundColor = Color.Transparent,
        indicator = { tabPositions ->
            TransactionTabIndicator(tabPositions, transactionType)
        },
        divider = { }
    ) {
        TransactionTab(
            boxColor = upArrowCircleColor(),
            arrowColor = upArrowColor(),
            iconId = R.drawable.ic_arrow_up_rotate,
            title = "Income",
            onClick = { onTabSelected(TransactionType.INCOME) },
        )
        TransactionTab(
            boxColor = downArrowCircleColor(),
            arrowColor = downArrowColor(),
            iconId = R.drawable.ic_arrow_down_rotate,
            title = "Expense",
            onClick = { onTabSelected(TransactionType.OUTCOME) }
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
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
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
    transactionType: TransactionType
) {
    val transition = updateTransition(transactionType)
    val indicatorLeft by transition.animateDp { page ->
        tabPositions[page.ordinal].left
    }
    val indicatorRight by transition.animateDp { page ->
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
                RoundedCornerShape(4.dp)
            )
    )
}
