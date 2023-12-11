package com.kstudio.diarymylife.domain

import com.kstudio.diarymylife.data.MoodUI
import com.kstudio.diarymylife.data.mood.MoodRepository
import com.kstudio.diarymylife.domain.mapping.mappingActivityEventToEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MoodUseCase(private val moodRepository: MoodRepository) {
    fun getMoodFromId(id: Long): Flow<MoodUI> =
        flow {
            moodRepository.getMoodFromID(id).map {
                MoodUI(
                    moodId = it.mood.moodId,
                    mood = it.mood.mood,
                    title = it.mood.title,
                    description = it.mood.description,
                    imageUri = it.mood.imageUri,
                    timestamp = it.mood.timestamp,
                    fileName = it.mood.fileName,
                    activityEvent = it.mood.activityEvent?.mappingActivityEventToEvent(),
                )
            }.collect {
                emit(it)
            }
        }
}