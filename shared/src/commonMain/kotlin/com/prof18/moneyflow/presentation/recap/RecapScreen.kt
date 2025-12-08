package com.prof18.moneyflow.presentation.recap

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.prof18.moneyflow.ui.style.Margins

@Composable
internal fun RecapScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Margins.regular)
            .padding(top = Margins.regular),
    ) {
        Text("Coming Soon")
    }
}
