package com.prof18.moneyflow.data.dropbox

expect class DropboxApi(

) {
     fun setup()
     fun startAuthorization()
     fun revokeAccess()
     fun performUpload()
     fun performDownload()
}

