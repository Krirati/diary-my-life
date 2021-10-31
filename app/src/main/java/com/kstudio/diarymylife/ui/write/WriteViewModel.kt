package com.kstudio.diarymylife.ui.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kstudio.diarymylife.ui.base.BaseViewModel

class WriteViewModel: BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is write Fragment"
    }
    val text: LiveData<String> = _text
}