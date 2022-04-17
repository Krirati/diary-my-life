package com.kstudio.diarymylife.data

import com.kstudio.diarymylife.model.ActivityDetail
import java.time.LocalDateTime

data class MoodRequest(
    val title: String,
    val description: String,
    val mood: String?,
    val activity: ArrayList<ActivityDetail>? = arrayListOf(),
    val imageName: String?,
    val timestamp: LocalDateTime,
    val createTime: LocalDateTime,
)
