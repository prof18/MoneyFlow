package com.prof18.moneyflow.features.settings

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dropbox.core.android.Auth
import com.prof18.moneyflow.BuildConfig
import com.prof18.moneyflow.MainActivity
import com.prof18.moneyflow.ui.style.AppMargins
import com.prof18.moneyflow.utils.registerForActivityResult

@Composable
fun SettingsScreen() {

    val viewModel = viewModel<SettingsViewModel>(
        factory = SettingsViewModelFactory()
    )

    val context = LocalContext.current

    val createFileURI = remember { mutableStateOf<Uri?>(null) }
    val createFileAction = registerForActivityResult(ActivityResultContracts.CreateDocument()) {
        createFileURI.value = it
    }
    createFileURI.value?.let { uri ->
        viewModel.performBackup(uri)
        Toast.makeText(context, "Export completed", Toast.LENGTH_SHORT).show()
    }

    val openFileURI = remember { mutableStateOf<Uri?>(null) }
    val openFileAction = registerForActivityResult(ActivityResultContracts.OpenDocument()) {
        openFileURI.value = it
    }
    openFileURI.value?.let { uri ->
        viewModel.performRestore(uri)
        Toast.makeText(context, "Import completed", Toast.LENGTH_SHORT).show()
    }

    Scaffold(
        topBar = {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.h4,
                modifier = Modifier
                    .padding(horizontal = AppMargins.regular)
                    .padding(top = AppMargins.regular)
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(top = AppMargins.regular)
            ) {
                Text(
                    "Import Database",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            openFileAction.launch(arrayOf("*/*"))
                        })
                        .padding(AppMargins.regular)
                )
                Divider()
                Text(
                    "Export Database",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            createFileAction.launch("MoneyFlowDB.db")
                        })
                        .padding(AppMargins.regular)
                )
                Divider()
                Text(
                    "Setup Dropbox",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            context.startActivity(Intent(context, DropboxLoginActivity::class.java))
                        })
                        .padding(AppMargins.regular)
                )
                Divider()
            }
        }
    )
}