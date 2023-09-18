package com.kstudio.diarymylife.ui.setting.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kstudio.diarymylife.data.shared_preferences.SharedPreferencesRepository
import com.kstudio.diarymylife.ui.base.BaseViewModel
import com.kstudio.diarymylife.utils.toLocalTime
import java.time.LocalTime


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

    fun saveNotificationTime(time: LocalTime?) {
        if (time == null) return
        appPreferenceRepository.dailyTime = time.toString()
    }

    private fun initNotification() {
        val enableNotification = appPreferenceRepository.enableNotification
        val dailyTime = appPreferenceRepository.dailyTime

        setIsEnableNotification(enableNotification && !dailyTime.isNullOrBlank())
        setIsDailyChange(dailyTime?.toLocalTime())
    }
}