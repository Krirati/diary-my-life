package com.kstudio.diarymylife.data.shared_preferences

import android.content.SharedPreferences
import java.time.LocalDateTime
import java.time.ZoneOffset

class SharedPreferencesRepositoryImpl constructor(
    private val sharedPreferences: SharedPreferences
) : SharedPreferencesRepository {

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

    override var enableDaily: Boolean
        get() = sharedPreferences.getBoolean(PrefsKeys.ENABLE_DAILY.name, true)
        set(value) {
            sharedPreferences.edit().putBoolean(PrefsKeys.ENABLE_DAILY.name, value)
                .apply()
        }

    override var dailyTime: Long
        get() = sharedPreferences.getLong(
            PrefsKeys.DAILY_TIME.name, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
        )
        set(value) {
            sharedPreferences.edit().putLong(PrefsKeys.DAILY_TIME.name, value).apply()
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
        ENABLE_DAILY("PREF_KEY_ENABLE_DAILY"),
        DAILY_TIME("PREF_KEY_DAILY_TIME"),
        FRESH_INSTALL("PREF_KEY_FRESH_INSTALL")
    }
}