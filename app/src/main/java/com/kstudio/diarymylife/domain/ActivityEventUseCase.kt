package com.kstudio.diarymylife.domain

import com.kstudio.diarymylife.data.activity_event.ActivityEventRepository
import com.kstudio.diarymylife.domain.mapping.mappingActivityEventToEvent
import com.kstudio.diarymylife.domain.model.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ActivityEventUseCase constructor(
    private val activityEventRepository: ActivityEventRepository
) {
    fun getAllEvent(): Flow<List<Event>> {
        return activityEventRepository.getAll().map { activityEvents ->
            activityEvents.mappingActivityEventToEvent()
        }
    }
}