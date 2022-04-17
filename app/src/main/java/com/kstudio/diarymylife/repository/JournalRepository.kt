package com.kstudio.diarymylife.repository

import android.util.Log
import com.kstudio.diarymylife.dao.JournalDao
import com.kstudio.diarymylife.data.Journal
import com.kstudio.diarymylife.data.JournalActivityEventCrossRef
import com.kstudio.diarymylife.data.MoodRequest
import com.kstudio.diarymylife.data.MoodWithActivity
import kotlinx.coroutines.flow.Flow

class JournalRepository(private val journalDao: JournalDao) {
    suspend fun insert(journal: MoodRequest) {
        val journalReq = Journal(
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
                JournalActivityEventCrossRef(
                    moodId = moodId,
                    eventId = it.eventId
                )
            )
        }
    }

    fun getJournal(): Flow<List<Journal>> {
        return journalDao.getJournalSortByTimestamp()
    }

    fun getMoodsAndActivities(): Flow<List<MoodWithActivity>> {
        return journalDao.getMoodsWithActivities()
    }

    fun getJournalFromID(journalID: Long): Flow<Journal> {
        return journalDao.getJournalFromJournalID(journalID = journalID)
    }

    suspend fun deleteJournal(journalID: Long) {
        return journalDao.deleteJournal(journalID)
    }

    fun updateJournal(journal: Journal) {
        journalDao.updateJournal(journal = journal)
    }


}