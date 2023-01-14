package com.kstudio.diarymylife.data

import java.time.LocalDateTime

data class MoodRequest(
    val moodId: Long?,
    val mood: Int?,
    val description: String,
    val activity: ArrayList<ActivityDetail>? = arrayListOf(),
    val imageName: String?,
    val timestamp: LocalDateTime,
    val createTime: LocalDateTime,
)
