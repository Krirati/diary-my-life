package com.kstudio.diarymylife.data.mood

import android.content.Context
import android.net.Uri
import com.kstudio.diarymylife.data.MoodRequest
import com.kstudio.diarymylife.database.dao.MoodDao
import com.kstudio.diarymylife.database.model.Mood
import com.kstudio.diarymylife.database.model.MoodWithActivity
import com.kstudio.diarymylife.utils.FileUtility
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

class MoodRepositoryImpl(
    private val context: Context,
    private val moodDao: MoodDao
) : MoodRepository {
    override suspend fun insert(mood: MoodRequest) {
        val fileName = UUID.randomUUID().toString()
        val moodReq = Mood(
            mood = mood.mood,
            description = mood.description,
            imageUri = mood.imageName,
            timestamp = mood.timestamp,
            createTime = mood.createTime,
            fileName = fileName
        )
        copyPhotoToInternalStorage(
            context = context,
            fileName = fileName,
            uri = mood.uri
        )

        moodDao.insert(moodReq)
    }

    override suspend fun updateMood(mood: MoodRequest?) {
        if (mood?.moodId == null) return
        val fileName =
            if (mood.fileName.isNullOrBlank()) UUID.randomUUID().toString() else mood.fileName
        val request = Mood(
            moodId = mood.moodId,
            mood = mood.mood,
            description = mood.description,
            imageUri = mood.imageName,
            timestamp = mood.timestamp,
            createTime = mood.createTime,
            fileName = fileName
        )

        copyPhotoToInternalStorage(
            context = context,
            fileName = fileName,
            uri = mood.uri
        )

        moodDao.updateMood(mood = request)
    }

    private fun copyPhotoToInternalStorage(
        context: Context,
        fileName: String,
        uri: Uri?
    ): Uri? {
        if (uri == null) return null

        val directory = FileUtility.getPhotoDirectory(context)
        val file = File(directory, fileName)
        val outputStream = FileOutputStream(file)
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        inputStream.use {
            outputStream.use {
                inputStream.copyTo(outputStream)
            }
        }
        return Uri.fromFile(file)
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
}