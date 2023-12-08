package com.kstudio.diarymylife.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kstudio.diarymylife.database.model.Profile
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(profile: Profile): Long

    @Query("SELECT * FROM profile")
    fun getFirst(): Flow<Profile>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateProfile(profile: Profile)
}