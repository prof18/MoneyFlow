package com.prof18.moneyflow.dropboxsync

import cocoapods.ObjectiveDropboxOfficial.*
import platform.Foundation.NSBundle
import platform.Foundation.NSDictionary
import platform.Foundation.NSURL
import platform.Foundation.create

class Dropbox {

    fun setup() {
        var apiKey = ""
        val path = NSBundle.mainBundle.pathForResource("Keys", "plist")
        if (path != null) {
            val dict = NSDictionary.create(contentsOfURL = NSURL(fileURLWithPath = path))
            apiKey = dict?.run {
                this.objectForKey("DropboxApiKey") as? String
            } ?: ""
        }
        DBClientsManager.setupWithAppKey(apiKey)
    }

    fun startAuthorization() {

    }

    fun revokeAccess() {

    }

    fun upload() {

    }

    fun restore() {

    }

}