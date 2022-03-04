package com.prof18.moneyflow.presentation.dropboxsync

import com.prof18.moneyflow.presentation.model.UIErrorMessage

sealed class DropboxSyncAction {
    class ShowError(val uiErrorMessage: UIErrorMessage) : DropboxSyncAction()
    object Loading : DropboxSyncAction()
    class Success(val message: String) : DropboxSyncAction()
}
