package com.kstudio.diarymylife.data.activity_event

import com.kstudio.diarymylife.database.dao.ActivityEventDao
import com.kstudio.diarymylife.database.model.ActivityEvent
import kotlinx.coroutines.flow.Flow

class ActivityEventRepositoryImpl(private val activityEventDao: ActivityEventDao) :
    ActivityEventRepository {
    override fun getAll(): Flow<List<ActivityEvent>> {
        return activityEventDao.getAll()
    }
}