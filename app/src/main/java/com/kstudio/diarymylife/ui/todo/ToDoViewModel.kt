package com.kstudio.diarymylife.ui.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kstudio.diarymylife.ui.base.BaseViewModel

class ToDoViewModel: BaseViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is todo Fragment"
    }
    val text: LiveData<String> = _text
}