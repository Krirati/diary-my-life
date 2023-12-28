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
        const val DATE_TIME_FORMAT = "HH:mm"
    }
}

class Moods {
    companion object {
        const val Excellent = 4
        const val Good = 3
        const val Average = 2
        const val Poor = 1
        const val Very_Poor = 0
        const val Mood_Avg = 10
        const val Mood_Tool = 11
    }

    val moodList = arrayListOf(
        Pair(Excellent, R.drawable.lol),
        Pair(Good, R.drawable.smile),
        Pair(Average, R.drawable.neutral),
        Pair(Poor, R.drawable.sad),
        Pair(Very_Poor, R.drawable.crying),
    )

    fun mapperThemeColor(mood: Int?): Pair<Int, Int> {
        return when (mood) {
            Excellent -> ColorsPair.excellent
            Good -> ColorsPair.good
            Average -> ColorsPair.average
            Poor -> ColorsPair.poor
            Very_Poor -> ColorsPair.veryPoor
            Mood_Tool -> ColorsPair.moodTool
            Mood_Avg -> ColorsPair.moodAvg
            else -> R.color.secondary_grey10 to R.drawable.bg_round_card_action
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

    fun mapMoodToPosition(mood: Int?): Int {
        return when (mood) {
            Excellent -> 0
            Good -> 1
            Average -> 2
            Poor -> 3
            Very_Poor -> 4
            else -> 0
        }
    }
}

class ColorsPair {
    companion object {
        val excellent = R.color.mood_excellent to R.drawable.bg_mood_excellent
        val good = R.color.mood_good to R.drawable.bg_mood_good
        val average = R.color.mood_average to R.drawable.bg_mood_average
        val poor = R.color.mood_pool to R.drawable.bg_mood_pool
        val veryPoor = R.color.mood_very_pool to R.drawable.bg_mood_very_pool
        val moodTool = R.color.new_york_pink to R.drawable.bg_round_new_york_pink
        val moodAvg = R.color.pewter_blue to R.drawable.bg_round_pewter_blue
        val primaryColor = R.color.amber_700 to R.color.xanthos
    }
}

enum class Gender(val gender: String) {
    MEN("Men"),
    WOMEN("Women"),
    OTHER("Other");

    companion object {
        infix fun fromString(value: String?): Gender =
            Gender.values().firstOrNull { it.gender == value } ?: OTHER
    }
}
