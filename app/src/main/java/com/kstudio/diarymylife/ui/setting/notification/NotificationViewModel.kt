package com.kstudio.diarymylife.ui.setting.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kstudio.diarymylife.ui.base.BaseViewModel
import java.time.LocalTime

class NotificationViewModel : BaseViewModel() {

    private val _isEnableNotification = MutableLiveData(false)
    val isEnableNotification: LiveData<Boolean> = _isEnableNotification

    private val _isDailyNotification = MutableLiveData(false)
    val isDailyNotification: LiveData<Boolean> = _isDailyNotification

    private val _isDailyChange = MutableLiveData<LocalTime?>(null)
    val isDailyChange: LiveData<LocalTime?> = _isDailyChange

    fun setIsEnableNotification(isEnable: Boolean) {
        _isEnableNotification.postValue(isEnable)
    }

    fun setIsDailyNotification(isEnable: Boolean) {
        _isDailyNotification.postValue(isEnable)
    }

    fun setIsDailyChange(time: LocalTime?) {
        _isDailyChange.postValue(time)
    }
}