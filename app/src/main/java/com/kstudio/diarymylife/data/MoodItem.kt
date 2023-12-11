package com.kstudio.diarymylife.data

import com.kstudio.diarymylife.domain.model.Event
import com.kstudio.diarymylife.ui.base.swipe_event.SwipeState
import java.time.LocalDateTime

data class MoodItem(
    var viewType: Int,
    var data: MoodUI?
)

data class MoodUI(
    val moodId: Long,
    val title: String,
    val description: String,
    val mood: Int?,
    val activityEvent: List<Event>?,
    val timestamp: LocalDateTime,
    val imageUri: String?,
    var state: SwipeState = SwipeState.NONE,
    val fileName: String?
)
