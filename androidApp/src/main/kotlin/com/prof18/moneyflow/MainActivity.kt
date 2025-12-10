package com.prof18.moneyflow

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import com.prof18.moneyflow.presentation.MoneyFlowApp
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : FragmentActivity() {

    private val viewModel: MainViewModel by viewModel()

    private val biometricAuthenticator by lazy { AndroidBiometricAuthenticator(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isDarkMode = (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) ==
            Configuration.UI_MODE_NIGHT_YES
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT,
            ) { isDarkMode },
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT,
            ) { isDarkMode },
        )
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE,
        )

        setContent {
            MoneyFlowApp(
                biometricAuthenticator = biometricAuthenticator,
            )
        }
    }

    override fun onResume() {
        super.onResume()
        performAuth()
    }

    override fun onStop() {
        super.onStop()
        viewModel.lockIfNeeded(biometricAuthenticator)
    }

    private fun performAuth() {
        viewModel.performAuthentication(biometricAuthenticator)
    }
}
