package com.kstudio.diarymylife.data.shared_preferences

interface SharedPreferencesRepository {
    var appTheme: String?
    var enableNotification: Boolean
    var enableDaily: Boolean
    var dailyTime: String?
    var freshInstall: Boolean
}