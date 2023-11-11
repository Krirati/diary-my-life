package com.kstudio.diarymylife.ui.create

import androidx.lifecycle.MutableLiveData
import com.kstudio.diarymylife.data.ActivityDetail
import com.kstudio.diarymylife.ui.base.BaseViewModel

class CreateMoodViewModel : BaseViewModel() {
    private var _selectsActivity: MutableLiveData<ArrayList<ActivityDetail>?> =
        MutableLiveData()
    var selectsActivity: MutableLiveData<ArrayList<ActivityDetail>?> = _selectsActivity

    fun addSelectActivity(data: ActivityDetail) {
        val temp = _selectsActivity.value
        if (temp != null && !temp.contains(data)) {
            temp.add(data)
            _selectsActivity.postValue(temp)
        } else {
            _selectsActivity.postValue(arrayListOf(data))
        }
    }

    fun removeSelectActivity(data: ActivityDetail) {
        val temp = _selectsActivity.value
        if (_selectsActivity.value?.contains(data) == true) {
            temp?.remove(data)
            _selectsActivity.postValue(temp!!)
        } else return
    }
}
