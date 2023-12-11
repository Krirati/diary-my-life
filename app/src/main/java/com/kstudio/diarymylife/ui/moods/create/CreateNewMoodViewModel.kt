package com.kstudio.diarymylife.ui.moods.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kstudio.diarymylife.data.mood.MoodRepository
import com.kstudio.diarymylife.domain.ProfileUseCase
import com.kstudio.diarymylife.domain.model.Event
import com.kstudio.diarymylife.ui.base.BaseMoodViewModel
import kotlinx.coroutines.launch

class CreateNewMoodViewModel(
    private val moodRepository: MoodRepository,
    private val profileUseCase: ProfileUseCase
) : BaseMoodViewModel() {

    private var _created: MutableLiveData<Unit> = MutableLiveData()
    var created: LiveData<Unit> = _created

    private var _nickname: MutableLiveData<String> = MutableLiveData()
    var nickname: LiveData<String> = _nickname

    private val _eventList = MutableLiveData<List<Event>>()
    val eventList: LiveData<List<Event>> = _eventList

    init {
        viewModelScope.launch {
            val nickname = profileUseCase.getProfile().nickname
            _nickname.postValue(nickname)
        }
    }

    fun createNewMood() {
        viewModelScope.launch {
            val res = moodRepository.insert(setupCreateMoodRequest())
            _created.postValue(res)
        }
    }

    fun updateEventSelectedListState(event: List<Event>) {
        _eventList.postValue(event.toMutableList())
    }

    fun removeEventSelectedList(event: Event) {
        val eventList = _eventList.value ?: return
        val newEventList = eventList.filterNot { it.eventId == event.eventId }
        _eventList.postValue(newEventList)
    }
}
