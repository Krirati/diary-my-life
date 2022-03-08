package com.kstudio.diarymylife.dao

import androidx.room.*
import com.kstudio.diarymylife.data.Journal
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(journal: Journal)

    @Query("SELECT * FROM journal_table ORDER BY timestamp DESC  LIMIT :limit")
    fun getJournalSortByTimestamp(limit: Int = 5): Flow<List<Journal>>

    @Query("SELECT * FROM journal_table WHERE id = :journalID")
    fun getJournalFromJournalID(journalID: Long): Flow<Journal>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateJournal(journal: Journal)

    @Query("DELETE FROM journal_table WHERE id = :journalID")
    suspend fun deleteJournal(journalID: Long)

}