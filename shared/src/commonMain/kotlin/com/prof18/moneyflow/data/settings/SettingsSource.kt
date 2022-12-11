package com.prof18.moneyflow.data.settings

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set

internal enum class SettingsFields {
    DROPBOX_CLIENT_CRED,
    USE_BIOMETRIC,
    HIDE_SENSITIVE_DATA,
    DROPBOX_LAST_UPLOAD_TIMESTAMP,
    DROPBOX_LAST_DOWNLOAD_TIMESTAMP,
    DROPBOX_LAST_UPLOAD_HASH,
    DROPBOX_LAST_DOWNLOAD_HASH,
}

@Suppress("TooManyFunctions")
class SettingsSource(private val settings: Settings) {

    fun getDropboxClientCred(): String? = settings.getStringOrNull(SettingsFields.DROPBOX_CLIENT_CRED.name)
    fun saveDropboxClientCred(clientCred: String) = settings.set(SettingsFields.DROPBOX_CLIENT_CRED.name, clientCred)
    fun deleteDropboxClientCred() = settings.remove(SettingsFields.DROPBOX_CLIENT_CRED.name)

    fun getUseBiometric(): Boolean = settings.getBoolean(SettingsFields.USE_BIOMETRIC.name, false)
    fun setUseBiometric(value: Boolean) = settings.set(SettingsFields.USE_BIOMETRIC.name, value)

    fun getHideSensitiveData(): Boolean = settings.getBoolean(SettingsFields.HIDE_SENSITIVE_DATA.name, false)
    fun setHideSensitiveData(value: Boolean) = settings.set(SettingsFields.HIDE_SENSITIVE_DATA.name, value)

    fun getLastDropboxUploadTimestamp(): Long? = settings.getLongOrNull(
        SettingsFields.DROPBOX_LAST_UPLOAD_TIMESTAMP.name,
    )
    fun setLastDropboxUploadTimestamp(value: Long) = settings.set(
        SettingsFields.DROPBOX_LAST_UPLOAD_TIMESTAMP.name,
        value,
    )
    fun deleteLastDropboxUploadTimestamp() = settings.remove(SettingsFields.DROPBOX_LAST_UPLOAD_TIMESTAMP.name)

    fun getLastDropboxDownloadTimestamp(): Long? = settings.getLongOrNull(
        SettingsFields.DROPBOX_LAST_DOWNLOAD_TIMESTAMP.name,
    )
    fun setLastDropboxDownloadTimestamp(value: Long) = settings.set(
        SettingsFields.DROPBOX_LAST_DOWNLOAD_TIMESTAMP.name,
        value,
    )
    fun deleteLastDropboxDownloadTimestamp() = settings.remove(SettingsFields.DROPBOX_LAST_DOWNLOAD_TIMESTAMP.name)

    fun getLastDropboxUploadHash(): String? = settings.getStringOrNull(SettingsFields.DROPBOX_LAST_UPLOAD_HASH.name)
    fun setLastDropboxUploadHash(value: String?) = settings.set(SettingsFields.DROPBOX_LAST_UPLOAD_HASH.name, value)
    fun deleteLastDropboxUploadHash() = settings.remove(SettingsFields.DROPBOX_LAST_UPLOAD_HASH.name)

    fun getLastDropboxDownloadHash(): String? = settings.getStringOrNull(SettingsFields.DROPBOX_LAST_DOWNLOAD_HASH.name)
    fun setLastDropboxDownloadHash(value: String?) = settings.set(SettingsFields.DROPBOX_LAST_DOWNLOAD_HASH.name, value)
    fun deleteLastDropboxDownloadHash() = settings.remove(SettingsFields.DROPBOX_LAST_DOWNLOAD_HASH.name)

    fun clearDropboxData() {
        deleteLastDropboxUploadHash()
        deleteLastDropboxUploadTimestamp()
        deleteLastDropboxDownloadHash()
        deleteLastDropboxDownloadTimestamp()
        deleteDropboxClientCred()
    }

    fun clear() = settings.clear()
}
