package com.kstudio.diarymylife.utils

import com.kstudio.diarymylife.R

class Keys {
    companion object {
        const val MOOD_ID = "mood_id"
    }
}

class Formats {
    companion object {
        const val DATE_FORMAT = "dd-MM-yyyy"
        const val DATE_FORMAT_APP = "MMMM, dd EEEE"
        const val DATE_TIME_FORMAT_APP = "MMMM, dd EEEE HH:mm"
    }
}

class BackgroundTheme {
    companion object {
        const val Excellent = 4
        const val Good = 3
        const val Average = 2
        const val Poor = 1
        const val Very_Poor = 0
    }

    fun mapperThemeCard(background: Int): Int {
        return when (background) {
            Excellent -> R.drawable.bg_round_laurel_green
            Good -> R.drawable.bg_round_pale_sprint_bud
            Average -> R.drawable.bg_round_lemon_yellow_crayola
            Poor -> R.drawable.bg_round_deep_champagne
            Very_Poor -> R.drawable.bg_round_sandy_brown
            else -> R.drawable.bg_round_card_action
        }
    }

    fun mapMoodStringToTitle(mood: Int?): String {
        return when (mood) {
            Excellent -> "Excellent"
            Good -> "Good"
            Average -> "Average"
            Poor -> "Poor"
            Very_Poor -> "Very Poor"
            else -> ""
        }
    }
}

enum class Gender(val gender: String) {
    MEN("Men"),
    WOMEN("Women"),
    OTHER("Other")
}

