package com.prof18.moneyflow.features.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prof18.moneyflow.ComposeNavigationFactory
import money_flow.shared.generated.resources.Res
import money_flow.shared.generated.resources.*
import com.prof18.moneyflow.Screen
import com.prof18.moneyflow.ui.components.SwitchWithText
import com.prof18.moneyflow.ui.style.Margins
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import co.touchlab.kermit.Logger
import org.koin.androidx.compose.koinViewModel

internal val SettingsScreenFactory: ComposeNavigationFactory =
    { navGraphBuilder: NavGraphBuilder, navController: NavController ->
        navGraphBuilder.composable(Screen.SettingsScreen.route) {
            val viewModel = koinViewModel<SettingsViewModel>()
            val hideDataState by viewModel.hideSensitiveDataState.collectAsState()
            val biometricState by viewModel.biometricState.collectAsState()

            SettingsScreen(
                performBackup = { uri -> viewModel.performBackup(BackupRequest(uri)) },
                performRestore = { uri -> viewModel.performRestore(BackupRequest(uri)) },
                biometricState = biometricState,
                onBiometricEnabled = { viewModel.updateBiometricState(it) },
                hideSensitiveDataState = hideDataState,
                onHideSensitiveDataEnabled = { viewModel.updateHideSensitiveDataState(it) },
            )
        }
    }

@Composable
internal fun SettingsScreen(
    performBackup: (Uri) -> Unit,
    performRestore: (Uri) -> Unit,
    biometricState: Boolean,
    onBiometricEnabled: (Boolean) -> Unit,
    hideSensitiveDataState: Boolean,
    onHideSensitiveDataEnabled: (Boolean) -> Unit,
) {

    val context = LocalContext.current

    val createFileURI = remember { mutableStateOf<Uri?>(null) }
    val createFileAction =
        rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument()) {
            createFileURI.value = it
        }
    createFileURI.value?.let { uri ->
        performBackup(uri)
        // TODO: move this toast from here?
        Toast.makeText(context, stringResource(Res.string.db_export_completed), Toast.LENGTH_SHORT)
            .show()
    }

    val openFileURI = remember { mutableStateOf<Uri?>(null) }
    val openFileAction = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {
        openFileURI.value = it
    }
    openFileURI.value?.let { uri ->
        performRestore(uri)
        // TODO: move this toast from here??
        Toast.makeText(context, stringResource(Res.string.db_import_completed), Toast.LENGTH_SHORT)
            .show()
    }

    SettingsScreenContent(
        onImportDatabaseClick = { openFileAction.launch(arrayOf("*/*")) },
        onExportDatabaseClick = { createFileAction.launch("MoneyFlowDB.db") },
        isBiometricSupported = isBiometricSupported(LocalContext.current),
        biometricState = biometricState,
        onBiometricEnabled = onBiometricEnabled,
        hideSensitiveDataState = hideSensitiveDataState,
        onHideSensitiveDataEnabled = onHideSensitiveDataEnabled,
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@Suppress("LongMethod") // TODO: reduce method length
private fun SettingsScreenContent(
    onImportDatabaseClick: () -> Unit,
    onExportDatabaseClick: () -> Unit,
    isBiometricSupported: Boolean,
    biometricState: Boolean,
    onBiometricEnabled: (Boolean) -> Unit,
    hideSensitiveDataState: Boolean,
    onHideSensitiveDataEnabled: (Boolean) -> Unit,
) {
    Scaffold(
        topBar = {
            Text(
                text = stringResource(Res.string.settings_screen),
                style = MaterialTheme.typography.h4,
                modifier = Modifier
                    .padding(horizontal = Margins.regular)
                    .padding(top = Margins.regular),
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(top = Margins.regular),
            ) {

                Text(
                    text = stringResource(Res.string.security),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = Margins.regular),
                )

                SwitchWithText(
                    onSwitchChanged = onHideSensitiveDataEnabled,
                    switchStatus = hideSensitiveDataState,
                    title = stringResource(Res.string.hide_sensitive_data),
                )

                if (isBiometricSupported) {
                    SwitchWithText(
                        onSwitchChanged = onBiometricEnabled,
                        switchStatus = biometricState,
                        title = stringResource(Res.string.biometric_support),
                    )
                }

                Text(
                    text = stringResource(Res.string.database_management),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = Margins.regular),
                )

                Text(
                    stringResource(Res.string.import_database),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onImportDatabaseClick() }
                        .padding(Margins.regular),
                )
                Divider()
                Text(
                    stringResource(Res.string.export_database),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onExportDatabaseClick() }
                        .padding(Margins.regular),
                )
            }
        },
    )
}

private fun isBiometricSupported(context: Context): Boolean {
    val biometricManager = BiometricManager.from(context)
    return when (isBiometricAuthPossible(biometricManager)) {
        BiometricManager.BIOMETRIC_SUCCESS -> true
            else -> {
                Logger.d { "Reached some auth state. It should be impossible to reach this state!" }
                false
            }
        }
    }

private fun isBiometricAuthPossible(biometricManager: BiometricManager) =
    biometricManager.canAuthenticate(Authenticators.BIOMETRIC_STRONG or Authenticators.DEVICE_CREDENTIAL)

@Preview(name = "Settings Light")
@Composable
private fun SettingsScreenPreview() {
    MoneyFlowTheme {
        Surface {
            SettingsScreenContent(
                onImportDatabaseClick = {},
                onExportDatabaseClick = {},
                biometricState = true,
                isBiometricSupported = true,
                onBiometricEnabled = {},
                hideSensitiveDataState = true,
                onHideSensitiveDataEnabled = {},
            )
        }
    }
}
