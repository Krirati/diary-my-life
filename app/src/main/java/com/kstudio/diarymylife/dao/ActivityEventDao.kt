package com.kstudio.diarymylife.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kstudio.diarymylife.data.ActivityEvent
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityEventDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg activityEvents: ActivityEvent?)

    @Query("SELECT * From activity_event")
    fun getAll(): Flow<List<ActivityEvent>>
}