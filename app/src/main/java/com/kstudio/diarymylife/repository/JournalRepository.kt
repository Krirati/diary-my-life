package com.kstudio.diarymylife.repository

import com.kstudio.diarymylife.dao.JournalDao
import com.kstudio.diarymylife.data.MoodRequest
import com.kstudio.diarymylife.entity.Mood
import com.kstudio.diarymylife.entity.relations.MoodActivityEventCrossRef
import com.kstudio.diarymylife.entity.relations.MoodWithActivity
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

    fun getJournal(): Flow<List<Mood>> {
        return journalDao.getJournalSortByTimestamp()
    }

    fun getMoodsAndActivities(): Flow<List<MoodWithActivity>> {
        return journalDao.getMoodsWithActivities()
    }

    fun getJournalFromID(journalID: Long): Flow<Mood> {
        return journalDao.getJournalFromJournalID(journalID = journalID)
    }

    suspend fun deleteJournal(journalID: Long) {
        return journalDao.deleteJournal(journalID)
    }

    fun updateJournal(journal: Mood) {
        journalDao.updateJournal(journal = journal)
    }


}