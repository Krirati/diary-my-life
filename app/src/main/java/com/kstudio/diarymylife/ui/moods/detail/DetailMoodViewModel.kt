package com.kstudio.diarymylife.ui.moods.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kstudio.diarymylife.data.mood.MoodRepository
import com.kstudio.diarymylife.domain.MoodUseCase
import com.kstudio.diarymylife.domain.ProfileUseCase
import com.kstudio.diarymylife.ui.base.BaseMoodViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailMoodViewModel(
    private val moodRepository: MoodRepository,
    private val profileUseCase: ProfileUseCase,
    private val moodUseCase: MoodUseCase
) : BaseMoodViewModel() {

    private var _updated: MutableLiveData<Unit> = MutableLiveData()
    val update: LiveData<Unit> = _updated
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
            moodUseCase.getMoodFromId(id).collect {
                setUpMoodDetail(it)
                it.activityEvent?.let { events -> updateEventSelectedListState(events) }
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
}
