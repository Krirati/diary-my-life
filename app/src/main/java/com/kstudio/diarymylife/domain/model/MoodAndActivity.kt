package com.kstudio.diarymylife.domain.model

import java.time.LocalDateTime

data class MoodAndActivityUI(
    val moodId: Long,
    val desc: String,
    val mood: Int?,
    val imageUri: String,
    val timestamp: LocalDateTime,
    val createTime: LocalDateTime,
    val fileName: String?,
)

data class MoodViewType(
    var viewType: Int,
    var data: MoodAndActivityUI?
)
