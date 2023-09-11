package com.kstudio.diarymylife.ui.detail.moodLanding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kstudio.diarymylife.data.MoodItem
import com.kstudio.diarymylife.data.MoodUI
import com.kstudio.diarymylife.data.mood.MoodRepository
import com.kstudio.diarymylife.database.model.MoodWithActivity
import com.kstudio.diarymylife.ui.adapter.ItemCardSwipeAdapter
import com.kstudio.diarymylife.ui.base.BaseMoodViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoodDetailLandingViewModel(
    private val moodRepository: MoodRepository,
) : BaseMoodViewModel(moodRepository) {

    private var _moodDetail: MutableLiveData<MoodItem?> = MutableLiveData()
    val moodData: MutableLiveData<MoodItem?> = _moodDetail

    private var _updated: MutableLiveData<Unit> = MutableLiveData()

    fun getMoodDetailFromID(id: Long) {
        viewModelScope.launch {
            moodRepository.getMoodFromID(id)
                .collect {
                    _moodDetail.postValue(mapToUI(it))
                }
        }
    }

    fun updateMoodDetail() {
        val moodId = _moodDetail.value?.data?.moodId ?: return

        viewModelScope.launch(Dispatchers.IO) {
            val req = setupUpdateMoodRequest(moodId)
            val res = moodRepository.updateMood(req)
            _updated.postValue(res)
        }
    }

    private fun mapToUI(mood: MoodWithActivity?): MoodItem? {
        return mood?.let {
            MoodItem(
                viewType = ItemCardSwipeAdapter.VIEW_ITEM,
                data = MoodUI(
                    moodId = it.mood.moodId,
                    desc = it.mood.description,
                    mood = it.mood.mood,
                    activity = it.activities.asActivityDetail(),
                    timestamp = it.mood.timestamp,
                    imageUri = it.mood.fileName ?: "",
                    fileName = it.mood.fileName ?: ""
                )
            )
        } ?: return null
    }
}