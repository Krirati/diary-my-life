package com.kstudio.diarymylife.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kstudio.diarymylife.data.ActivityDetail
import com.kstudio.diarymylife.database.model.ActivityEvent

open class BaseViewModel : ViewModel() {

    private val _currentScreen = MutableLiveData<Int>()
    val currentScreen: LiveData<Int> = _currentScreen

    fun selectCurrentPage(page: Int) {
        _currentScreen.postValue(page)
    }

    fun List<ActivityEvent>.asActivityDetail() = map {
        ActivityDetail(
            eventId = it.eventId,
            activityName = it.activityName,
            activityImage = it.activityImage
        )
    }
}
