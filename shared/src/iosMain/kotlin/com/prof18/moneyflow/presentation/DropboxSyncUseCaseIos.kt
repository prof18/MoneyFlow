package com.prof18.moneyflow.presentation

import com.prof18.moneyflow.FlowWrapper
import com.prof18.moneyflow.domain.entities.DropboxClientStatus
import com.prof18.moneyflow.domain.entities.MoneyFlowResult
import com.prof18.moneyflow.domain.entities.doOnError
import com.prof18.moneyflow.dropbox.DropboxDownloadParam
import com.prof18.moneyflow.dropbox.DropboxDownloadResult
import com.prof18.moneyflow.dropbox.DropboxUploadParam
import com.prof18.moneyflow.presentation.dropboxsync.DropboxSyncMetadataModel
import com.prof18.moneyflow.presentation.dropboxsync.DropboxSyncUseCase
import com.prof18.moneyflow.presentation.model.UIErrorMessage
import kotlinx.coroutines.launch

@ObjCName("DropboxSyncUseCase")
class DropboxSyncUseCaseIos(
    private val dropboxSyncUseCase: DropboxSyncUseCase,
) : BaseUseCaseIos() {

    val dropboxClientStatus: FlowWrapper<DropboxClientStatus> =
        FlowWrapper(scope, dropboxSyncUseCase.dropboxClientStatus)

    fun observeDropboxSyncMetadataModel(): FlowWrapper<DropboxSyncMetadataModel> =
        FlowWrapper(scope, dropboxSyncUseCase.observeDropboxSyncMetadataModel())

    fun setupClient(apiKey: String) = dropboxSyncUseCase.setupClient(apiKey)

    fun handleOAuthResponse(platformOAuthResponseHandler: () -> Unit) =
        dropboxSyncUseCase.handleOAuthResponse(platformOAuthResponseHandler)

    fun startAuthFlow(platformAuthHandler: () -> Unit) =
        dropboxSyncUseCase.startAuthFlow(platformAuthHandler)

    fun restoreDropboxClient(
        onError: (UIErrorMessage) -> Unit,
    ) {
        scope.launch {
            val result = dropboxSyncUseCase.restoreDropboxClient()
            result.doOnError { uiErrorMessage ->
                onError(uiErrorMessage)
            }
        }
    }

    fun saveDropboxAuth(
        onError: (UIErrorMessage) -> Unit,
    ) {
        scope.launch {
            val result = dropboxSyncUseCase.saveDropboxAuth()
            result.doOnError { uiErrorMessage ->
                onError(uiErrorMessage)
            }
        }
    }

    fun unlink() {
        scope.launch {
            dropboxSyncUseCase.unlinkDropbox()
        }
    }

    fun upload(
        dropboxUploadParam: DropboxUploadParam,
        onSuccess: () -> Unit,
        onError: (UIErrorMessage) -> Unit,
    ) {
        scope.launch {
            when (val result = dropboxSyncUseCase.upload(dropboxUploadParam)) {
                is MoneyFlowResult.Success -> onSuccess()
                is MoneyFlowResult.Error -> onError(result.uiErrorMessage)
            }
        }
    }

    fun download(
        downloadParam: DropboxDownloadParam,
        onSuccess: (DropboxDownloadResult) -> Unit,
        onError: (UIErrorMessage) -> Unit,
    ) {
        scope.launch {
            when (val result = dropboxSyncUseCase.download(downloadParam)) {
                is MoneyFlowResult.Success -> onSuccess(result.data)
                is MoneyFlowResult.Error -> onError(result.uiErrorMessage)
            }
        }
    }
}
