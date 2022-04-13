package com.kstudio.diarymylife.ui.create.select_activity

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.ui.base.BaseViewModel


class SelectActivityViewModel(
    private val application: Application
) : BaseViewModel() {
    private var _listActivity: MutableLiveData<ArrayList<Pair<String, String>>> = MutableLiveData()
    var listActivity = _listActivity


    fun updateListActivity() {
        val iconName: String = application.resources.getResourceEntryName(R.drawable.ic_pencil)
        val iconName2: String = application.resources.getResourceEntryName(R.drawable.ic_arrow_left)
        val iconName3: String = application.resources.getResourceEntryName(R.drawable.ic_very_happy)

        val arrayList = arrayListOf(
            Pair("run", iconName),
            Pair("foot", iconName2),
            Pair("swim", iconName3),
            Pair("bas", iconName2),
            Pair("golf", iconName),
            Pair("tenis", iconName3),
        )
        _listActivity.postValue(arrayList)
    }
}