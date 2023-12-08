package com.kstudio.diarymylife.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activity_event")
data class ActivityEvent(
    @PrimaryKey(autoGenerate = true) val eventId: Int,
    @ColumnInfo(name = "activity_name") val activityName: String,
    @ColumnInfo(name = "activity_image") val activityImage: String,
)
