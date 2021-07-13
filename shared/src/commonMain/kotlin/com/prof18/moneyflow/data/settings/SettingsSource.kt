package com.prof18.moneyflow.data.settings

import co.touchlab.stately.freeze
import com.russhwolf.settings.*

enum class SettingsFields {
    DROPBOX_CLIENT_CRED,
    DROPBOX_LAST_SYNC,
    USE_BIOMETRIC
}

class SettingsSource(private val settings: Settings) {

    init {
        freeze()
    }

    fun getDropboxLastSync(): Long? = settings.getLongOrNull(SettingsFields.DROPBOX_LAST_SYNC.name)
    fun saveDropboxLastSync(lastSync: Long) = settings.set(SettingsFields.DROPBOX_LAST_SYNC.name, lastSync)

    fun getDropboxClientCred(): String? = settings.getStringOrNull(SettingsFields.DROPBOX_CLIENT_CRED.name)
    fun saveDropboxClientCred(clientCred: String) = settings.set(SettingsFields.DROPBOX_CLIENT_CRED.name, clientCred)

    fun getUseBiometric(): Boolean = settings.getBoolean(SettingsFields.USE_BIOMETRIC.name, false)
    fun setUseBiometric(value: Boolean) = settings.set(SettingsFields.USE_BIOMETRIC.name, value)

    fun clear() = settings.clear()
}