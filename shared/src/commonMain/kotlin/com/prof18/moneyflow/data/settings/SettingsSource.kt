package com.prof18.moneyflow.data.settings

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set

internal enum class SettingsFields {
    USE_BIOMETRIC,
    HIDE_SENSITIVE_DATA,
}

internal class SettingsSource(private val settings: Settings) {

    fun getUseBiometric(): Boolean = settings.getBoolean(SettingsFields.USE_BIOMETRIC.name, false)
    fun setUseBiometric(value: Boolean) = settings.set(SettingsFields.USE_BIOMETRIC.name, value)

    fun getHideSensitiveData(): Boolean = settings.getBoolean(SettingsFields.HIDE_SENSITIVE_DATA.name, false)
    fun setHideSensitiveData(value: Boolean) = settings.set(SettingsFields.HIDE_SENSITIVE_DATA.name, value)

    fun clear() = settings.clear()
}
