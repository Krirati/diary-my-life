package com.kstudio.diarymylife.data.mood

import com.kstudio.diarymylife.data.MoodRequest
import com.kstudio.diarymylife.database.dao.MoodDao
import com.kstudio.diarymylife.database.model.Mood
import com.kstudio.diarymylife.database.model.MoodActivityEventCrossRef
import com.kstudio.diarymylife.database.model.MoodWithActivity
import kotlinx.coroutines.flow.Flow

class MoodRepositoryImpl(private val moodDao: MoodDao) : MoodRepository {
    override suspend fun insert(mood: MoodRequest) {
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

    override fun getMoodsAndActivitiesWithLimit(): Flow<List<MoodWithActivity>> {
        return moodDao.getMoodsWithActivitiesWithLimit()
    }

    override fun getMoodsAndActivities(): Flow<List<MoodWithActivity>> {
        return moodDao.getMoodsWithActivities()
    }

    override fun getMoodFromID(moodID: Long): Flow<MoodWithActivity> {
        return moodDao.getMoodFromMoodID(moodID = moodID)
    }

    override suspend fun deleteMood(moodID: Long) {
        return moodDao.deleteMood(moodID)
    }

    override suspend fun updateMood(mood: MoodRequest?) {
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