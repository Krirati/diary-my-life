package com.kstudio.diarymylife.ui.mood.moodLanding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kstudio.diarymylife.data.MoodItem
import com.kstudio.diarymylife.data.MoodUI
import com.kstudio.diarymylife.database.model.MoodWithActivity
import com.kstudio.diarymylife.data.mood.MoodRepositoryImpl
import com.kstudio.diarymylife.ui.adapter.ItemCardSwipeAdapter
import com.kstudio.diarymylife.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class MoodLandingViewModel(
    private val moodRepositoryImpl: MoodRepositoryImpl
) : BaseViewModel() {

    private var _moodDetail: MutableLiveData<MoodItem?> = MutableLiveData()
    val moodData: MutableLiveData<MoodItem?> = _moodDetail

    fun getMoodDetailFromID(id: Long) {
        viewModelScope.launch {
            moodRepositoryImpl.getMoodFromID(id)
                .collect {
                    _moodDetail.postValue(mapToUI(it))
                }

        }
    }

    private fun mapToUI(mood: MoodWithActivity?): MoodItem? {
        return mood?.let {
            MoodItem(
                viewType = ItemCardSwipeAdapter.VIEW_ITEM,
                data = MoodUI(
                    moodId = it.mood.moodId,
                    title = it.mood.title,
                    desc = it.mood.description,
                    mood = it.mood.mood,
                    activity = it.activities.asActivityDetail(),
                    timestamp = it.mood.timestamp,
                    imageId = "",
                )
            )
        }
            ?: return null
    }
}