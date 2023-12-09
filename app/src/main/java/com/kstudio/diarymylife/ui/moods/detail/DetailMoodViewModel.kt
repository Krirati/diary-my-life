package com.kstudio.diarymylife.ui.moods.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kstudio.diarymylife.data.MoodItem
import com.kstudio.diarymylife.data.MoodUI
import com.kstudio.diarymylife.data.mood.MoodRepository
import com.kstudio.diarymylife.database.model.MoodWithActivity
import com.kstudio.diarymylife.domain.ProfileUseCase
import com.kstudio.diarymylife.ui.adapter.ItemCardSwipeAdapter
import com.kstudio.diarymylife.ui.base.BaseMoodViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailMoodViewModel(
    private val moodRepository: MoodRepository,
    private val profileUseCase: ProfileUseCase
) : BaseMoodViewModel() {

    private var _updated: MutableLiveData<Unit> = MutableLiveData()
    private var _nickname: MutableLiveData<String> = MutableLiveData()
    var nickname: LiveData<String> = _nickname

    init {
        viewModelScope.launch {
            val nickname = profileUseCase.getProfile().nickname
            _nickname.postValue(nickname)
        }
    }
    fun getMoodDetailFromID(id: Long) {
        viewModelScope.launch {
            moodRepository.getMoodFromID(id)
                .collect {
                    setUpMoodDetail(mapToUI(it))
                }
        }
    }

    fun updateMoodDetail() {
        viewModelScope.launch(Dispatchers.IO) {
            val req = setupUpdateMoodRequest()
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
                    title = it.mood.title,
                    desc = it.mood.description,
                    mood = it.mood.mood,
                    activity = it.activities.asActivityDetail(),
                    timestamp = it.mood.timestamp,
                    imageUri = it.mood.imageUri ?: "",
                    fileName = it.mood.fileName ?: ""
                )
            )
        } ?: return null
    }
}
