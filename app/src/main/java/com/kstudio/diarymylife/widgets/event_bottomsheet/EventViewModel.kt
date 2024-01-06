package com.kstudio.diarymylife.widgets.event_bottomsheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kstudio.diarymylife.domain.ActivityEventUseCase
import com.kstudio.diarymylife.domain.model.Event
import com.kstudio.diarymylife.widgets.event_bottomsheet.model.EventState
import kotlinx.coroutines.launch

class EventViewModel(
    private val activityEventUseCase: ActivityEventUseCase
) : ViewModel() {

    private val _eventListState = MutableLiveData<List<EventState>>()
    val eventListState: LiveData<List<EventState>> = _eventListState

    private val _eventSelectedListState = MutableLiveData<List<Event>>()
    val eventSelectedListState: LiveData<List<Event>> = _eventSelectedListState

    fun getEventsList() {
        viewModelScope.launch {
            activityEventUseCase.getAllEvent().collect {
                if (it.isEmpty()) {
                    val state = listOf(EventState.Add)
                    state.emitEvent()
                } else {
                    val state = listOf(EventState.Events(it))
                    state.emitEvent()
                }
            }
        }
    }

    private fun List<EventState>.emitEvent() {
        _eventListState.postValue(this)
    }

    fun createNewEvent() {
        // Do in next release
    }

    fun removeEvent() {
        // Do in next release
    }

    fun updateEventSelectedListState(event: List<Event>) {
        _eventSelectedListState.postValue(event)
    }
}