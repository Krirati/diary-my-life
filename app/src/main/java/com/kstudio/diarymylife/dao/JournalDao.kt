package com.kstudio.diarymylife.dao

import androidx.room.*
import com.kstudio.diarymylife.data.ActivityEvent
import com.kstudio.diarymylife.data.Journal
import com.kstudio.diarymylife.data.JournalActivityEventCrossRef
import com.kstudio.diarymylife.data.MoodWithActivity
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(journal: Journal): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivityEvent(journal: ActivityEvent)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudentSubjectCrossRef(crossRef: JournalActivityEventCrossRef)

    @Query("SELECT * FROM user_mood ORDER BY timestamp DESC  LIMIT :limit")
    fun getJournalSortByTimestamp(limit: Int = 5): Flow<List<Journal>>

    @Transaction
    @Query("SELECT * FROM user_mood ORDER BY timestamp DESC  LIMIT :limit")
    fun getMoodsWithActivities(limit: Int = 5): Flow<List<MoodWithActivity>>

    @Query("SELECT * FROM user_mood WHERE moodId = :journalID")
    fun getJournalFromJournalID(journalID: Long): Flow<Journal>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateJournal(journal: Journal)

    @Query("DELETE FROM user_mood WHERE moodId = :journalID")
    suspend fun deleteJournal(journalID: Long)

}