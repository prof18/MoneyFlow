package com.prof18.moneyflow

import androidx.lifecycle.ViewModel
import com.prof18.moneyflow.presentation.main.MainUseCase

internal class MainViewModel(
    private val mainUseCase: MainUseCase,
) : ViewModel() {

    fun isBiometricEnabled(): Boolean {
        return mainUseCase.isBiometricEnabled()
    }
}
