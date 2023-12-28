package com.kstudio.diarymylife.domain.model

import com.kstudio.diarymylife.database.model.ActivityEvent
import java.time.LocalDateTime

data class MoodAndActivityUI(
    val moodId: Long,
    val title: String,
    val desc: String,
    val mood: Int?,
    val imageUri: String,
    val timestamp: LocalDateTime,
    val createTime: LocalDateTime,
    val fileName: String?,
    val activityEvent: List<ActivityEvent>?
)

data class MoodViewType(
    var viewType: Int,
    var data: MoodAndActivityUI?
)
