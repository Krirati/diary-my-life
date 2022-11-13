package com.kstudio.diarymylife.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MoodWithActivity(
    @Embedded val mood: Mood,
    @Relation(
        entity = ActivityEvent::class,
        parentColumn = "moodId",
        entityColumn = "eventId",
        associateBy = Junction(MoodActivityEventCrossRef::class)
    )
    val activities: List<ActivityEvent>
)
