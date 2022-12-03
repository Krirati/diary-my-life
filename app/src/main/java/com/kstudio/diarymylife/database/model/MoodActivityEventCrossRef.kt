package com.kstudio.diarymylife.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["moodId", "eventId"])
data class MoodActivityEventCrossRef(
    val moodId: Long,
    @ColumnInfo(index = true)
    val eventId: Int
)
