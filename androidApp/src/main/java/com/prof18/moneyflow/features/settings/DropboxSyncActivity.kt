package com.prof18.moneyflow.features.settings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.prof18.moneyflow.BuildConfig
import com.prof18.moneyflow.R
import com.prof18.moneyflow.dropboxapi.DropboxAuthorizationParam
import com.prof18.moneyflow.presentation.addtransaction.AddTransactionAction
import com.prof18.moneyflow.presentation.dropboxsync.DropboxSyncAction
import com.prof18.moneyflow.ui.style.AppMargins
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class DropboxSyncActivity : ComponentActivity() {

    private val viewModel: DropboxSyncViewModel by viewModel()
    private var isAuthOngoing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoneyFlowTheme {

                val lastRefresh by viewModel.lastRefresh.collectAsState()
                val isConnected by viewModel.isDropboxConnected.collectAsState()

                DropboxLoginContent(
                    lastRefreshTimestamp = lastRefresh,
                    isConnected = isConnected,
                    connectDropbox = {
                        val authParam = DropboxAuthorizationParam(
                            activity = this@DropboxSyncActivity,
                            apiKey = BuildConfig.DROPBOX_APP_KEY,
                        )
                        viewModel.startAuthFlow(authParam)
                        isAuthOngoing = true
                    },
                    backupOnDropbox = {
                        // TODO: implement backup
                        //                        viewModel.backup()
                    },
                    unlinkDropbox = {
                        viewModel.unlinkDropbox()
                    },
                    dropboxSyncAction = viewModel.dropboxSyncAction,
                    resetAction = { viewModel.resetAction() },
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isAuthOngoing) {
            viewModel.saveDropboxAuth()
            isAuthOngoing = false
        }
    }
}

@Composable
private fun DropboxLoginContent(
    lastRefreshTimestamp: String?,
    isConnected: Boolean,
    connectDropbox: () -> Unit,
    backupOnDropbox: () -> Unit,
    unlinkDropbox: () -> Unit,
    dropboxSyncAction: DropboxSyncAction?,
    resetAction: () -> Unit,
) {

    val scaffoldState = rememberScaffoldState()
    dropboxSyncAction?.let {
        when (it) {
            is DropboxSyncAction.ShowError -> {
                LaunchedEffect(scaffoldState.snackbarHostState) {
                    val uiErrorMessage = it.uiErrorMessage
                    val message = "${uiErrorMessage.message}\n${uiErrorMessage.nerdMessage}"
                    scaffoldState.snackbarHostState.showSnackbar(message)
                    resetAction()
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            Column(
                modifier = Modifier
                    .padding(horizontal = AppMargins.regular)
                    .padding(top = AppMargins.regular)
            ) {
                Text(
                    text = stringResource(R.string.dropbox_sync),
                    style = MaterialTheme.typography.h4,
                )

                Text(
                    if (lastRefreshTimestamp != null) {
                        "${stringResource(id = R.string.dropbox_last_refresh)} $lastRefreshTimestamp"
                    } else {
                        stringResource(id = R.string.dropbox_not_sync_yet)
                    },
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(top = AppMargins.small)
                )
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(top = AppMargins.regular)
            ) {

                if (isConnected) {

                    Text(
                        stringResource(R.string.dropbox_linked),
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier
                            .padding(AppMargins.regular)
                    )

                    Text(
                        stringResource(R.string.backup_to_dropbox),
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { backupOnDropbox() }
                            .padding(AppMargins.regular)
                    )
                    Divider()
                    Text(
                        stringResource(R.string.restore_from_dropbox),
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { backupOnDropbox() }
                            .padding(AppMargins.regular)
                    )
                    Divider()
                    Text(
                        stringResource(R.string.unlink_dropbox),
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { unlinkDropbox() }
                            .padding(AppMargins.regular)
                    )
                    Divider()

                } else {
                    Text(
                        stringResource(R.string.connect_dropbox),
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { connectDropbox() }
                            .padding(AppMargins.regular)
                    )
                    Divider()
                }
            }
        }
    )
}

@Preview
@Composable
fun DropboxLoginContentLightNotConnectedPreview() {
    MoneyFlowTheme {
        Surface {
            DropboxLoginContent(
                lastRefreshTimestamp = null,
                isConnected = false,
                connectDropbox = { },
                backupOnDropbox = { },
                unlinkDropbox = {},
                dropboxSyncAction = null,
                resetAction = {},
            )
        }
    }
}

@Preview
@Composable
fun DropboxLoginContentLightConnectedPreview() {
    MoneyFlowTheme {
        Surface {
            DropboxLoginContent(
                lastRefreshTimestamp = "12 July 2021",
                isConnected = true,
                connectDropbox = { },
                backupOnDropbox = { },
                unlinkDropbox = {},
                dropboxSyncAction = null,
                resetAction = {},
            )
        }
    }
}


@Preview
@Composable
fun DropboxLoginContentDarkNotConnectedPreview() {
    MoneyFlowTheme(darkTheme = true) {
        Surface {
            DropboxLoginContent(
                lastRefreshTimestamp = null,
                isConnected = false,
                connectDropbox = { },
                backupOnDropbox = { },
                unlinkDropbox = {},
                dropboxSyncAction = null,
                resetAction = {},
            )
        }
    }
}

@Preview
@Composable
fun DropboxLoginContentDarkConnectedPreview() {
    MoneyFlowTheme(darkTheme = true) {
        Surface {
            DropboxLoginContent(
                lastRefreshTimestamp = "12 July 2021",
                isConnected = true,
                connectDropbox = { },
                backupOnDropbox = { },
                unlinkDropbox = {},
                dropboxSyncAction = null,
                resetAction = {},
            )
        }
    }
}
