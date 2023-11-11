package com.kstudio.diarymylife.data.activity_event

import com.kstudio.diarymylife.database.model.ActivityEvent
import kotlinx.coroutines.flow.Flow

interface ActivityEventRepository {

    fun getAll(): Flow<List<ActivityEvent>>
}
