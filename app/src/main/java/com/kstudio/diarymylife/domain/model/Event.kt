package com.kstudio.diarymylife.domain.model

data class Event(
    val eventId : Int,
    val icon: Int,
    val activityName: String,
    val backgroundColor: Int,
    val activityImage: Int,
    val isSelected: Boolean = false
)
