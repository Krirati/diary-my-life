package com.kstudio.diarymylife.repository

import com.kstudio.diarymylife.dao.ActivityEventDao
import com.kstudio.diarymylife.entity.ActivityEvent
import kotlinx.coroutines.flow.Flow

class ActivityEventRepository(private val activityEventDao: ActivityEventDao) {
    fun getAll() : Flow<List<ActivityEvent>> {
        return activityEventDao.getAll()
    }
}