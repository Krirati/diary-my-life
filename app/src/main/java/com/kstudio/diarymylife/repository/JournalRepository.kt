package com.kstudio.diarymylife.repository

import com.kstudio.diarymylife.dao.JournalDao
import com.kstudio.diarymylife.data.Journal
import kotlinx.coroutines.flow.Flow

class JournalRepository(private val journalDao: JournalDao) {
    suspend fun insert(journal: Journal) {
        journalDao.insert(journal)
    }

    fun getJournal(): Flow<List<Journal>> {
        return journalDao.getJournalSortByTimestamp()
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