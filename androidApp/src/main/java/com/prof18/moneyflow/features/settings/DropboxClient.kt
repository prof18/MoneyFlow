package com.prof18.moneyflow.features.settings

import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.v2.DbxClientV2
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import presentation.dropboxsync.DropboxSyncUserCase
import timber.log.Timber
import java.util.*


class DropboxClient(
    private val dropboxSyncUserCase: DropboxSyncUserCase
) {

    private var parentJob = Job()
    private val scope = CoroutineScope(parentJob + Dispatchers.IO)

    private var dbxClientV2: DbxClientV2? = null

    private val dropboxClientLinkStatus = MutableStateFlow(DropboxClientStatus.NOT_LINKED)
    fun observeClientStatus(): StateFlow<DropboxClientStatus> = dropboxClientLinkStatus

    init {
        scope.launch {
            val accessToken = dropboxSyncUserCase.getAccessTokenSuspendable()
            Timber.d("Dropbox access token: $accessToken")
            if (accessToken != null) {
                createDropboxClient(accessToken)
            }
        }
    }

    fun setTokenAndCreateClient(accessToken: String) {
        createDropboxClient(accessToken)
    }

    private fun createDropboxClient(accessToken: String) {
        val userLocale: String = Locale.getDefault().toString()
        val requestConfig = DbxRequestConfig
            .newBuilder("moneyflowapp")
            .withUserLocale(userLocale)
            .build()
        dbxClientV2 = DbxClientV2(requestConfig, accessToken)
        dropboxClientLinkStatus.value = DropboxClientStatus.LINKED
        Timber.d("Dropbox client status: ${if (dbxClientV2 == null) "null" else "not null"}")
    }

    fun dispose() {
        scope.cancel()
    }
}