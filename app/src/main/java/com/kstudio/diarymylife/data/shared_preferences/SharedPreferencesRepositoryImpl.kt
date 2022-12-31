package com.kstudio.diarymylife.data.shared_preferences

import android.content.SharedPreferences

class SharedPreferencesRepositoryImpl constructor(
    private val sharedPreferences: SharedPreferences
): SharedPreferencesRepository {

    override var appTheme: String?
        get() = sharedPreferences.getString(PrefsKeys.APP_THEME.name, "") ?: ""
        set(value) {
            sharedPreferences.edit().putString(PrefsKeys.APP_THEME.name, value).apply()
        }

    override var enableNotification: Boolean
        get() = sharedPreferences.getBoolean(PrefsKeys.ENABLE_NOTIFICATION.name, true)
        set(value) {
            sharedPreferences.edit().putBoolean(PrefsKeys.ENABLE_NOTIFICATION.name, value)
                .apply()
        }

    override var freshInstall: Boolean
        get() = sharedPreferences.getBoolean(PrefsKeys.FRESH_INSTALL.name, false)
        set(value) {
            sharedPreferences.edit().putBoolean(PrefsKeys.FRESH_INSTALL.name, value)
                .apply()
        }

    enum class PrefsKeys(val value: String) {
        APP_THEME("PREF_KEY_APP_THEME"),
        ENABLE_NOTIFICATION("PREF_KEY_ENABLE_NOTIFICATION"),
        FRESH_INSTALL("PREF_KEY_FRESH_INSTALL")
    }
}