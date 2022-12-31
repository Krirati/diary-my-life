package com.kstudio.diarymylife.data.shared_preferences

interface SharedPreferencesRepository {
    var appTheme: String?
    var enableNotification: Boolean
    var freshInstall: Boolean
}