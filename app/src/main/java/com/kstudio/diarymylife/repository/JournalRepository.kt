package com.kstudio.diarymylife.repository

import com.kstudio.diarymylife.database.dao.JournalDao
import com.kstudio.diarymylife.data.MoodRequest
import com.kstudio.diarymylife.database.model.Mood
import com.kstudio.diarymylife.database.model.MoodActivityEventCrossRef
import com.kstudio.diarymylife.database.model.MoodWithActivity
import kotlinx.coroutines.flow.Flow

class JournalRepository(private val journalDao: JournalDao) {
    suspend fun insert(journal: MoodRequest) {
        val journalReq = Mood(
            title = journal.title,
            description = journal.description,
            timestamp = journal.timestamp,
            createTime = journal.createTime,
            imageName = journal.imageName,
            mood = journal.mood,
            activity = null
        )
        val moodId = journalDao.insert(journalReq)
        journal.activity?.forEach {
            journalDao.insertStudentSubjectCrossRef(
                MoodActivityEventCrossRef(
                    moodId = moodId,
                    eventId = it.eventId
                )
            )
        }
    }

    fun getMoodsAndActivitiesWithLimit(): Flow<List<MoodWithActivity>> {
        return journalDao.getMoodsWithActivitiesWithLimit()
    }

    fun getMoodsAndActivities(): Flow<List<MoodWithActivity>> {
        return journalDao.getMoodsWithActivities()
    }

    fun getJournalFromID(journalID: Long): Flow<MoodWithActivity> {
        return journalDao.getJournalFromJournalID(journalID = journalID)
    }

    suspend fun deleteJournal(journalID: Long) {
        return journalDao.deleteJournal(journalID)
    }

    suspend fun updateJournal(journal: MoodRequest?) {
        if (journal?.moodId == null) return
        val request = Mood(
            moodId = journal.moodId,
            title = journal.title,
            description = journal.description,
            mood = journal.mood.orEmpty(),
            activity = null,
            imageName = journal.imageName,
            timestamp = journal.timestamp,
            createTime = journal.createTime,
        )
        journalDao.updateJournal(journal = request)
        journal.activity?.forEach {
            journalDao.insertStudentSubjectCrossRef(
                MoodActivityEventCrossRef(
                    moodId = journal.moodId,
                    eventId = it.eventId
                )
            )
        }
    }


}