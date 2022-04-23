package com.kstudio.diarymylife.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "user_mood")
data class Mood(
    @PrimaryKey(autoGenerate = true ) val moodId: Long = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "mood") val mood: String?,
    @ColumnInfo(name = "activity") val activity: ArrayList<Int>? = arrayListOf(),
    @ColumnInfo(name = "image_name") val imageName: String?,
    @ColumnInfo(name = "timestamp") val timestamp: LocalDateTime,
    @ColumnInfo(name = "create_time") val createTime: LocalDateTime,
)
