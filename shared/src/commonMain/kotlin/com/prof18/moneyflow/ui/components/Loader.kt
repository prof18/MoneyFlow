package com.prof18.moneyflow.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

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
@Composable
private fun LoaderPreview() {
    Surface {
        MoneyFlowTheme {
            Loader()
        }
    }
}
