package com.kstudio.diarymylife.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "user_mood")
data class Mood(
    @PrimaryKey(autoGenerate = true) val moodId: Long = 0,
    @ColumnInfo(name = "mood") val mood: Int?,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "image_uri") val imageUri: String?,
    @ColumnInfo(name = "timestamp") val timestamp: LocalDateTime,
    @ColumnInfo(name = "create_time") val createTime: LocalDateTime,
    @ColumnInfo(name = "fileName") val fileName: String?,
)
