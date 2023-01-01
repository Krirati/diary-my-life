package com.kstudio.diarymylife.data.mood

import com.kstudio.diarymylife.data.MoodRequest
import com.kstudio.diarymylife.database.model.MoodWithActivity
import kotlinx.coroutines.flow.Flow

interface MoodRepository {
    suspend fun insert(mood: MoodRequest)

    fun getMoodsAndActivitiesWithLimit(): Flow<List<MoodWithActivity>>

    fun getMoodsAndActivities(): Flow<List<MoodWithActivity>>

    fun getMoodFromID(moodID: Long): Flow<MoodWithActivity>

    suspend fun deleteMood(moodID: Long)

    suspend fun updateMood(mood: MoodRequest?)

}