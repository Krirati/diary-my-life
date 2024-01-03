package com.kstudio.diarymylife.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kstudio.diarymylife.ui.base.BaseViewModel

class SettingViewModel : BaseViewModel() {

    private val _navigateEvent = MutableLiveData<SettingNavigate?>()
    val navigateEvent: LiveData<SettingNavigate?> = _navigateEvent

    fun emitSettingNavigate(event: SettingNavigate) {
        _navigateEvent.postValue(event)
    }
}
