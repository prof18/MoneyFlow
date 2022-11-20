package com.prof18.moneyflow.features.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prof18.moneyflow.ComposeNavigationFactory
import com.prof18.moneyflow.R
import com.prof18.moneyflow.Screen
import com.prof18.moneyflow.ui.components.SwitchWithText
import com.prof18.moneyflow.ui.style.Margins
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import org.koin.androidx.compose.getViewModel
import timber.log.Timber

internal object SettingsScreenFactory : ComposeNavigationFactory {
    override fun create(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(Screen.SettingsScreen.route) {
            val viewModel = getViewModel<SettingsViewModel>()
            val hideDataState by viewModel.hideSensitiveDataState.collectAsState()

            SettingsScreen(
                performBackup = { uri -> viewModel.performBackup(uri) },
                performRestore = { uri -> viewModel.performRestore(uri) },
                biometricState = viewModel.biometricState,
                onBiometricEnabled = { viewModel.updateBiometricState(it) },
                hideSensitiveDataState = hideDataState,
                onHideSensitiveDataEnabled = { viewModel.updateHideSensitiveDataState(it) },
            )
        }
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
        Toast.makeText(context, stringResource(R.string.db_export_completed), Toast.LENGTH_SHORT)
            .show()
    }

    val openFileURI = remember { mutableStateOf<Uri?>(null) }
    val openFileAction = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {
        openFileURI.value = it
    }
    openFileURI.value?.let { uri ->
        performRestore(uri)
        // TODO: move this toast from here??
        Toast.makeText(context, stringResource(R.string.db_import_completed), Toast.LENGTH_SHORT)
            .show()
    }

    SettingsScreenContent(
        onImportDatabaseClick = { openFileAction.launch(arrayOf("*/*")) },
        onExportDatabaseClick = { createFileAction.launch("MoneyFlowDB.db") },
        openDropboxSetup = {
            context.startActivity(
                Intent(
                    context,
                    DropboxSyncActivity::class.java,
                ),
            )
        },
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
    openDropboxSetup: () -> Unit,
    isBiometricSupported: Boolean,
    biometricState: Boolean,
    onBiometricEnabled: (Boolean) -> Unit,
    hideSensitiveDataState: Boolean,
    onHideSensitiveDataEnabled: (Boolean) -> Unit,
) {
    Scaffold(
        topBar = {
            Text(
                text = stringResource(id = R.string.settings_screen),
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
                    text = stringResource(R.string.security),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = Margins.regular),
                )

                SwitchWithText(
                    onSwitchChanged = onHideSensitiveDataEnabled,
                    switchStatus = hideSensitiveDataState,
                    title = stringResource(R.string.hide_sensitive_data),
                )

                if (isBiometricSupported) {
                    SwitchWithText(
                        onSwitchChanged = onBiometricEnabled,
                        switchStatus = biometricState,
                        title = stringResource(R.string.biometric_support),
                    )
                }

                Text(
                    text = stringResource(R.string.database_management),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = Margins.regular),
                )

                Text(
                    stringResource(R.string.import_database),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onImportDatabaseClick() }
                        .padding(Margins.regular),
                )
                Divider()
                Text(
                    stringResource(R.string.export_database),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onExportDatabaseClick() }
                        .padding(Margins.regular),
                )
                Divider()
                Text(
                    stringResource(R.string.setup_dropbox),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { openDropboxSetup() }
                        .padding(Margins.regular),
                )
                Divider()
            }
        },
    )
}

private fun isBiometricSupported(context: Context): Boolean {
    val biometricManager = BiometricManager.from(context)
    return when (isBiometricAuthPossible(biometricManager)) {
        BiometricManager.BIOMETRIC_SUCCESS -> true
        else -> {
            Timber.d("Reached some auth state. It should be impossible to reach this state!")
            false
        }
    }
}

private fun isBiometricAuthPossible(biometricManager: BiometricManager) =
    biometricManager.canAuthenticate(Authenticators.BIOMETRIC_STRONG or Authenticators.DEVICE_CREDENTIAL)

@Preview(name = "Settings Light")
@Preview(name = "Settings Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SettingsScreenPreview() {
    MoneyFlowTheme {
        Surface {
            SettingsScreenContent(
                onImportDatabaseClick = {},
                onExportDatabaseClick = {},
                openDropboxSetup = {},
                biometricState = true,
                isBiometricSupported = true,
                onBiometricEnabled = {},
                hideSensitiveDataState = true,
                onHideSensitiveDataEnabled = {},
            )
        }
    }
}
