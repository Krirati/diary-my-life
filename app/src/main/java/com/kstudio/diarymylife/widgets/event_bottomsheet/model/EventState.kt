package com.kstudio.diarymylife.widgets.event_bottomsheet.model

import com.kstudio.diarymylife.domain.model.Event

sealed class EventState {
    object Add : EventState()
    data class Events(val events: List<Event>) : EventState()
}