package com.kstudio.diarymylife.ui.setting.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kstudio.diarymylife.data.shared_preferences.SharedPreferencesRepository
import com.kstudio.diarymylife.ui.base.BaseViewModel
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset


class NotificationViewModel constructor(
    private val appPreferenceRepository: SharedPreferencesRepository
) : BaseViewModel() {

    private val _isEnableNotification = MutableLiveData<Boolean>(null)
    val isEnableNotification: LiveData<Boolean?> = _isEnableNotification

    private val _isDailyTimeChange = MutableLiveData<LocalTime?>(null)
    val isDailyTimeChange: LiveData<LocalTime?> = _isDailyTimeChange

    private val _performBottomSheetSetTime = MutableLiveData<Unit>()
    val performBottomSheetSetTime: LiveData<Unit> = _performBottomSheetSetTime

    init {
        initNotification()
    }

    fun setIsEnableNotification(isEnable: Boolean) {
        _isEnableNotification.postValue(isEnable)
        appPreferenceRepository.enableNotification = isEnable
    }

    fun setIsDailyChange(time: LocalTime?) {
        _isDailyTimeChange.postValue(time)
    }

    fun setPerformBottomSheetSetTime() {
        _performBottomSheetSetTime.postValue(Unit)
    }

    private fun initNotification() {
        val enableNotification = appPreferenceRepository.enableNotification
        val dailyTime = appPreferenceRepository.dailyTime
        val dateTime = LocalDateTime.ofEpochSecond(dailyTime, 0, ZoneOffset.UTC)
        setIsEnableNotification(enableNotification)
        setIsDailyChange(dateTime.toLocalTime())
    }
}