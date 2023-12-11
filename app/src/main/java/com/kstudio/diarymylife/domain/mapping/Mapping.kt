package com.kstudio.diarymylife.domain.mapping

import com.kstudio.diarymylife.database.model.ActivityEvent
import com.kstudio.diarymylife.domain.model.Event

fun List<ActivityEvent>.mappingActivityEventToEvent() =
    this.map {
        Event(
            eventId = it.eventId,
            icon = it.activityImage,
            activityName = it.activityName,
            backgroundColor = it.activityColor
        )
    }