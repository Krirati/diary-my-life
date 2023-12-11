package com.kstudio.diarymylife.widgets.event_bottomsheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kstudio.diarymylife.domain.ActivityEventUseCase
import com.kstudio.diarymylife.widgets.event_bottomsheet.model.EventState
import kotlinx.coroutines.launch

class EventViewModel(
    private val activityEventUseCase: ActivityEventUseCase
) : ViewModel() {

    private val _eventListState = MutableLiveData<List<EventState>>()
    val eventListState: LiveData<List<EventState>> = _eventListState

    fun getEventsList() {
        viewModelScope.launch {
            activityEventUseCase.getAllEvent().collect {
                if (it.isEmpty()) {
                    val state = listOf(EventState.Add)
                    state.emitEvent()
                } else {
                    val state = listOf(EventState.Events(it), EventState.Add)
                    state.emitEvent()
                }
            }
        }
    }

    private fun List<EventState>.emitEvent() {
        _eventListState.postValue(this)
    }

    fun createNewEvent() {

    }

    fun removeEvent() {

    }
}