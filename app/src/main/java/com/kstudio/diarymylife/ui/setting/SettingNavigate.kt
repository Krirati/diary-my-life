package com.kstudio.diarymylife.ui.setting

sealed class SettingNavigate {
    object Notification: SettingNavigate()
    object Profile: SettingNavigate()
    object License: SettingNavigate()
}
