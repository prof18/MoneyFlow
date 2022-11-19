package com.prof18.moneyflow.presentation

import com.prof18.moneyflow.FlowWrapper
import com.prof18.moneyflow.domain.entities.DatabaseDownloadData
import com.prof18.moneyflow.domain.entities.DatabaseUploadData
import com.prof18.moneyflow.domain.entities.DropboxClientStatus
import com.prof18.moneyflow.domain.entities.MoneyFlowResult
import com.prof18.moneyflow.domain.entities.doOnError
import com.prof18.moneyflow.dropbox.DropboxAuthorizationParam
import com.prof18.moneyflow.dropbox.DropboxDownloadResult
import com.prof18.moneyflow.dropbox.DropboxHandleOAuthRequestParam
import com.prof18.moneyflow.dropbox.DropboxSetupParam
import com.prof18.moneyflow.presentation.dropboxsync.DropboxSyncMetadataModel
import com.prof18.moneyflow.presentation.dropboxsync.DropboxSyncUseCase
import com.prof18.moneyflow.presentation.model.UIErrorMessage
import kotlinx.coroutines.launch

class DropboxSyncUseCaseIos(
    private val dropboxSyncUseCase: DropboxSyncUseCase,
) : BaseUseCaseIos() {

    val dropboxClientStatus: FlowWrapper<DropboxClientStatus> =
        FlowWrapper(scope, dropboxSyncUseCase.dropboxClientStatus)

    fun observeDropboxSyncMetadataModel(): FlowWrapper<DropboxSyncMetadataModel> =
        FlowWrapper(scope, dropboxSyncUseCase.observeDropboxSyncMetadataModel())

    fun setupClient(setupParam: DropboxSetupParam) = dropboxSyncUseCase.setupClient(setupParam)

    fun handleOAuthResponse(oAuthRequestParam: DropboxHandleOAuthRequestParam) =
        dropboxSyncUseCase.handleOAuthResponse(oAuthRequestParam)

    fun startAuthFlow(authorizationParam: DropboxAuthorizationParam) =
        dropboxSyncUseCase.startAuthFlow(authorizationParam)

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
        databaseUploadData: DatabaseUploadData,
        onSuccess: () -> Unit,
        onError: (UIErrorMessage) -> Unit,
    ) {
        scope.launch {
            when (val result = dropboxSyncUseCase.upload(databaseUploadData)) {
                is MoneyFlowResult.Success -> onSuccess()
                is MoneyFlowResult.Error -> onError(result.uiErrorMessage)
            }
        }
    }

    fun download(
        databaseDownloadData: DatabaseDownloadData,
        onSuccess: (DropboxDownloadResult) -> Unit,
        onError: (UIErrorMessage) -> Unit,
    ) {
        scope.launch {
            when (val result = dropboxSyncUseCase.download(databaseDownloadData)) {
                is MoneyFlowResult.Success -> onSuccess(result.data)
                is MoneyFlowResult.Error -> onError(result.uiErrorMessage)
            }
        }
    }
}
