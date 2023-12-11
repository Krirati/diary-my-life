package com.kstudio.diarymylife.domain.model

data class Event(
    val eventId : Int,
    val icon: String,
    val title: String,
    val backgroundColor: Int,
    val isSelected: Boolean = false
)
