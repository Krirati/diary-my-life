package com.kstudio.diarymylife.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kstudio.diarymylife.ui.base.BaseViewModel

class SettingViewModel: BaseViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is todo Fragment"
    }
    val text: LiveData<String> = _text
}