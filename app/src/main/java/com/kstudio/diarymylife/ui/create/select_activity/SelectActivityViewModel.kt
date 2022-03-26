package com.kstudio.diarymylife.ui.create.select_activity

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.ui.base.BaseViewModel


class SelectActivityViewModel(
    private val application: Application
) : BaseViewModel() {
    private var _listActivity: MutableLiveData<ArrayList<String>> = MutableLiveData()
    var listActivity = _listActivity

    fun updateListActivity() {
        val iconName: String = application.resources.getResourceEntryName(R.drawable.ic_pencil)
        val iconName2: String = application.resources.getResourceEntryName(R.drawable.ic_arrow_left)
        val arrayList = arrayListOf(
            iconName,
            iconName2,
            iconName,
            iconName2,
            iconName,
            iconName2,
        )
        _listActivity.postValue(arrayList)
    }
}