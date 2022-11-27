package com.kstudio.diarymylife.utils

import com.kstudio.diarymylife.R

fun mapMoodStringToRes(mood: String): Int {
    return when (mood) {
        "1" -> R.drawable.mood1
        "2" -> R.drawable.mood2
        "3" -> R.drawable.mood3
        "4" -> R.drawable.mood4
        "5" -> R.drawable.mood5
        else -> R.drawable.mood3
    }
}
