package com.kstudio.diarymylife.ui.create.select_mood

import androidx.lifecycle.MutableLiveData
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.ui.base.BaseViewModel

class SelectMoodViewModel() : BaseViewModel() {
    private var _listMood : MutableLiveData<ArrayList<Int>> = MutableLiveData()
    var listMood = _listMood

    fun updateListMood() {
        val arrayList = arrayListOf(
            R.drawable.ic_pencil,
            R.drawable.ic_line,
            R.drawable.ic_pencil,
            R.drawable.ic_line,
            R.drawable.ic_pencil,
        )
        _listMood.postValue(arrayList)
    }
}