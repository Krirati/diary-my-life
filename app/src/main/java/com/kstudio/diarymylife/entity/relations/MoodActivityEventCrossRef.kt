package com.kstudio.diarymylife.entity.relations

import androidx.room.Entity

@Entity(primaryKeys = ["moodId", "eventId"])
data class MoodActivityEventCrossRef(
    val moodId: Long,
    val eventId: Int
)
