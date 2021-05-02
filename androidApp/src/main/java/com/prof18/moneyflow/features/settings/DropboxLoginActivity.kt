package com.prof18.moneyflow.features.settings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dropbox.core.android.Auth
import com.prof18.moneyflow.BuildConfig
import com.prof18.moneyflow.R
import com.prof18.moneyflow.ui.style.AppMargins
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class DropboxLoginActivity : ComponentActivity() {

    private val viewModel: DropboxLoginViewModel by viewModel()
    private var hasPerformLogin = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoneyFlowTheme {
                Scaffold(
                    topBar = {

                        val lastRefresh by viewModel.lastRefresh.collectAsState()

                        Column(
                            modifier = Modifier
                                .padding(horizontal = AppMargins.regular)
                                .padding(top = AppMargins.regular)
                        ) {
                            Text(
                                text = "Dropbox Sync",
                                style = MaterialTheme.typography.h4,

                                )

                            Text(
                                if (lastRefresh != null) {
                                    "${stringResource(id = R.string.dropbox_last_refresh)} $lastRefresh!!"
                                } else {
                                    stringResource(id = R.string.dropbox_not_sync_yet)
                                },
                                style = MaterialTheme.typography.body2,
                                modifier = Modifier.padding(top = AppMargins.small)
                            )
                        }
                    },
                    content = {

                        val isConnected by viewModel.isDropboxConnected.collectAsState()

                        Column(
                            modifier = Modifier
                                .padding(top = AppMargins.regular)
                        ) {

                            if (isConnected) {

                                Text(
                                    "Dropbox Linked",
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier
                                        .padding(AppMargins.regular)
                                )


                                Text(
                                    "Backup to Dropbox",
                                    style = MaterialTheme.typography.h6,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable(onClick = {
                                            viewModel.backup()
                                        })
                                        .padding(AppMargins.regular)
                                )
                                Divider()
                                Text(
                                    "Restore from Dropbox",
                                    style = MaterialTheme.typography.h6,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable(onClick = {
                                            viewModel.backup()
                                        })
                                        .padding(AppMargins.regular)
                                )
                                Divider()
                                Text(
                                    "Unlink",
                                    style = MaterialTheme.typography.h6,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable(onClick = {
                                            viewModel.unlinkDropbox()
                                        })
                                        .padding(AppMargins.regular)
                                )
                                Divider()

                            } else {
                                Text(
                                    "Connect Dropbox",
                                    style = MaterialTheme.typography.h6,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable(onClick = {
                                            Auth.startOAuth2Authentication(
                                                this@DropboxLoginActivity,
                                                BuildConfig.DROPBOX_APP_KEY
                                            )
                                            hasPerformLogin = true
                                        })
                                        .padding(AppMargins.regular)
                                )
                                Divider()
                            }


                        }
                    }
                )
            }
        }
    }


    override fun onResume() {
        super.onResume()
        Timber.d("onResum")
        if (hasPerformLogin) {
            viewModel.saveAccessToken()
            hasPerformLogin = false
        }
    }
}