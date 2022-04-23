package com.kstudio.diarymylife.entity.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.kstudio.diarymylife.entity.ActivityEvent
import com.kstudio.diarymylife.entity.Mood

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
