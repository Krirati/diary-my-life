package com.kstudio.diarymylife.database.dao

import androidx.room.*
import com.kstudio.diarymylife.database.model.ActivityEvent
import com.kstudio.diarymylife.database.model.Mood
import com.kstudio.diarymylife.database.model.MoodActivityEventCrossRef
import com.kstudio.diarymylife.database.model.MoodWithActivity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mood: Mood): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivityEvent(activityEvent: ActivityEvent)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudentSubjectCrossRef(crossRef: MoodActivityEventCrossRef)

    @Query("SELECT * FROM user_mood ORDER BY timestamp DESC  LIMIT :limit")
    fun getMoodSortByTimestamp(limit: Int = 3): Flow<List<Mood>>

    @Transaction
    @Query("SELECT * FROM user_mood ORDER BY timestamp DESC  LIMIT :limit")
    fun getMoodsWithActivitiesWithLimit(limit: Int = 3): Flow<List<MoodWithActivity>>

    @Transaction
    @Query("SELECT * FROM user_mood ORDER BY timestamp DESC")
    fun getMoodsWithActivities(): Flow<List<MoodWithActivity>>

    @Transaction
    @Query("SELECT * FROM user_mood WHERE moodId = :moodID")
    fun getMoodFromMoodID(moodID: Long): Flow<MoodWithActivity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateMood(mood: Mood)

    @Query("DELETE FROM user_mood WHERE moodId = :moodID")
    suspend fun deleteMood(moodID: Long)

}