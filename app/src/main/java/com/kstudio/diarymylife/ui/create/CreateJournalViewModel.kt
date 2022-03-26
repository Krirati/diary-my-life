package com.kstudio.diarymylife.ui.create

import androidx.lifecycle.MutableLiveData
import com.kstudio.diarymylife.ui.base.BaseViewModel

class CreateJournalViewModel:BaseViewModel() {

    private var _selects: MutableLiveData<Int> = MutableLiveData()
    var selects = _selects

    fun setupSelectMood(index: Int) {
        _selects.postValue(index)
    }
}