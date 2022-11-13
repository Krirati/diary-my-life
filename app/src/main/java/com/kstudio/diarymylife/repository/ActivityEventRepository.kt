package com.kstudio.diarymylife.repository

import com.kstudio.diarymylife.database.dao.ActivityEventDao
import com.kstudio.diarymylife.database.model.ActivityEvent
import kotlinx.coroutines.flow.Flow

class ActivityEventRepository(private val activityEventDao: ActivityEventDao) {
    fun getAll() : Flow<List<ActivityEvent>> {
        return activityEventDao.getAll()
    }
}