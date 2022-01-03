package com.prof18.moneyflow.dropbox

expect class DropboxApi {
     fun setup()
     fun startAuthorization(authData: DropboxAuthorizationData)
     fun handleOAuthResponse(oAuthRequestData: DropboxHandleOAuthRequestData)
     fun getClient(clientData: DropboxClientData): DropboxClient?
     fun revokeAccess()
     fun performUpload(uploadData: DropboxUploadData)
     fun performDownload(downloadData: DropboxDownloadData)
     // TODO: maybe add a method to get the credentials?
}

expect class DropboxClient

expect class DropboxAuthorizationData

expect class DropboxHandleOAuthRequestData

expect class DropboxUploadData

expect class DropboxDownloadData

expect class DropboxClientData