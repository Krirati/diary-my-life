package com.kstudio.diarymylife.data.mood

import android.content.Context
import com.kstudio.diarymylife.data.MoodRequest
import com.kstudio.diarymylife.database.dao.MoodDao
import com.kstudio.diarymylife.database.model.ActivityEvent
import com.kstudio.diarymylife.database.model.Mood
import com.kstudio.diarymylife.database.model.MoodWithActivity
import com.kstudio.diarymylife.domain.model.Event
import com.kstudio.diarymylife.utils.FileUtility.copyPhotoToInternalStorage
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class MoodRepositoryImpl(
    private val context: Context,
    private val moodDao: MoodDao
) : MoodRepository {
    override suspend fun insert(mood: MoodRequest) {
        val fileName = if (mood.uri == null) null else UUID.randomUUID().toString()

        val moodReq = Mood(
            mood = mood.mood,
            title = mood.title,
            description = mood.description,
            imageUri = mood.imageName,
            timestamp = mood.timestamp,
            createTime = mood.createTime,
            fileName = fileName,
            activityEvent = mood.activity.mappingToActivityEvent()
        )
        if (mood.uri != null && fileName != null) {
            copyPhotoToInternalStorage(
                context = context,
                fileName = fileName,
                uri = mood.uri
            )
        }

        moodDao.insert(moodReq)
    }

    override suspend fun updateMood(mood: MoodRequest?) {
        if (mood?.moodId == null) return
        val fileName =
            if (mood.fileName.isNullOrBlank()) UUID.randomUUID().toString() else mood.fileName
        val request = Mood(
            moodId = mood.moodId,
            mood = mood.mood,
            title = mood.title,
            description = mood.description,
            imageUri = mood.imageName,
            timestamp = mood.timestamp,
            createTime = mood.createTime,
            fileName = fileName,
            activityEvent = mood.activity.mappingToActivityEvent()
        )

        if (mood.uri != null) {
            copyPhotoToInternalStorage(
                context = context,
                fileName = fileName,
                uri = mood.uri
            )
        }

        moodDao.updateMood(mood = request)
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

    private fun List<Event>?.mappingToActivityEvent(): List<ActivityEvent>? {
        return this?.map {
            ActivityEvent(
                eventId = it.eventId,
                activityName = it.activityName,
                activityImage = it.icon,
                activityColor = it.backgroundColor
            )
        }
    }
}
