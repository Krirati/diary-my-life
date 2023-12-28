package com.kstudio.diarymylife.domain.model

data class ActivityEventCount(
    val activityImage: Int,
    val activityName: String = "",
    val count: Int = 0
)
