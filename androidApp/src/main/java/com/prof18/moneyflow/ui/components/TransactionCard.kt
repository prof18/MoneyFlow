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
import androidx.ui.tooling.preview.Preview
import com.prof18.moneyflow.R
import com.prof18.moneyflow.style.AppColors
import com.prof18.moneyflow.style.AppMargins
import com.prof18.moneyflow.style.MoneyFlowTheme


// TODO: pass real data

@Composable
fun TransactionCard(

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
                    asset = vectorResource(id = R.drawable.ic_hamburger),
                    modifier = Modifier.gravity(Alignment.CenterVertically)
                        .padding(AppMargins.small)
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
                    text = "Dinner with friends in a very special Pellentesque habitant morbi tristique senectus et netus.",
                    style = MaterialTheme.typography.subtitle1
                )

                Text(
                    text = "21 Jan 2020",
                    style = MaterialTheme.typography.caption
                )
            }
        }

        Text(
            text = "-1500 €",
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