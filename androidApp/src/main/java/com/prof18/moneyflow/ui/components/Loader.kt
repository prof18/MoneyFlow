package com.prof18.moneyflow.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.prof18.moneyflow.ui.style.MoneyFlowTheme

@Composable
internal fun Loader() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        CircularProgressIndicator()
    }
}

@Preview(name = "Loader Light")
@Preview(name = "Loader Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LoaderPreview() {
    Surface {
        MoneyFlowTheme {
            Loader()
        }
    }
}
