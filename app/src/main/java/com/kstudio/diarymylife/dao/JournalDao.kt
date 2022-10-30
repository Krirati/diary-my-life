package com.kstudio.diarymylife.dao

import androidx.room.*
import com.kstudio.diarymylife.entity.ActivityEvent
import com.kstudio.diarymylife.entity.Mood
import com.kstudio.diarymylife.entity.relations.MoodActivityEventCrossRef
import com.kstudio.diarymylife.entity.relations.MoodWithActivity
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mood: Mood): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivityEvent(journal: ActivityEvent)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudentSubjectCrossRef(crossRef: MoodActivityEventCrossRef)

    @Query("SELECT * FROM user_mood ORDER BY timestamp DESC  LIMIT :limit")
    fun getJournalSortByTimestamp(limit: Int = 3): Flow<List<Mood>>

    @Transaction
    @Query("SELECT * FROM user_mood ORDER BY timestamp DESC  LIMIT :limit")
    fun getMoodsWithActivitiesWithLimit(limit: Int = 3): Flow<List<MoodWithActivity>>

    @Transaction
    @Query("SELECT * FROM user_mood ORDER BY timestamp DESC")
    fun getMoodsWithActivities(): Flow<List<MoodWithActivity>>

    @Query("SELECT * FROM user_mood WHERE moodId = :journalID")
    fun getJournalFromJournalID(journalID: Long): Flow<Mood>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateJournal(journal: Mood)

    @Query("DELETE FROM user_mood WHERE moodId = :journalID")
    suspend fun deleteJournal(journalID: Long)

}