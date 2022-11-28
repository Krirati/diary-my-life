package com.kstudio.diarymylife.repository

import com.kstudio.diarymylife.database.dao.MoodDao
import com.kstudio.diarymylife.data.MoodRequest
import com.kstudio.diarymylife.database.model.Mood
import com.kstudio.diarymylife.database.model.MoodActivityEventCrossRef
import com.kstudio.diarymylife.database.model.MoodWithActivity
import kotlinx.coroutines.flow.Flow

class MoodRepository(private val moodDao: MoodDao) {
    suspend fun insert(mood: MoodRequest) {
        val moodReq = Mood(
            title = mood.title,
            description = mood.description,
            timestamp = mood.timestamp,
            createTime = mood.createTime,
            imageName = mood.imageName,
            mood = mood.mood,
            activity = null
        )
        val moodId = moodDao.insert(moodReq)
        mood.activity?.forEach {
            moodDao.insertStudentSubjectCrossRef(
                MoodActivityEventCrossRef(
                    moodId = moodId,
                    eventId = it.eventId
                )
            )
        }
    }

    fun getMoodsAndActivitiesWithLimit(): Flow<List<MoodWithActivity>> {
        return moodDao.getMoodsWithActivitiesWithLimit()
    }

    fun getMoodsAndActivities(): Flow<List<MoodWithActivity>> {
        return moodDao.getMoodsWithActivities()
    }

    fun getMoodFromID(moodID: Long): Flow<MoodWithActivity> {
        return moodDao.getMoodFromMoodID(moodID = moodID)
    }

    suspend fun deleteMood(moodID: Long) {
        return moodDao.deleteMood(moodID)
    }

    suspend fun updateMood(mood: MoodRequest?) {
        if (mood?.moodId == null) return
        val request = Mood(
            moodId = mood.moodId,
            title = mood.title,
            description = mood.description,
            mood = mood.mood,
            activity = null,
            imageName = mood.imageName,
            timestamp = mood.timestamp,
            createTime = mood.createTime,
        )
        moodDao.updateMood(mood = request)
        mood.activity?.forEach {
            moodDao.insertStudentSubjectCrossRef(
                MoodActivityEventCrossRef(
                    moodId = mood.moodId,
                    eventId = it.eventId
                )
            )
        }
    }
}