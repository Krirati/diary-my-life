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

class BackgroundTheme{
    companion object {
        const val BEIGE = 6
        const val NYANZA = 5
        const val LIGHT_GOLDENROD_YELLOW = 4
        const val LEMON_CHIFFON = 3
        const val NAVAJO_WHITE = 2
        const val MACARONI_AND_CHEESE = 1
        const val SANDY_BROWN = 0
    }

    fun mapperThemeCard(background: Int): Int {
        return when (background) {
            BEIGE -> R.drawable.bg_round_beige
            NYANZA -> R.drawable.bg_round_nyanza
            LIGHT_GOLDENROD_YELLOW -> R.drawable.bg_round_light_goldenrod_yellow
            LEMON_CHIFFON -> R.drawable.bg_round_lemon_chiffon
            NAVAJO_WHITE -> R.drawable.bg_round_navajo_white
            MACARONI_AND_CHEESE -> R.drawable.bg_round_macaroni_and_cheese
            SANDY_BROWN -> R.drawable.bg_round_sandy_brown
            else -> R.drawable.bg_round_beige
        }
    }
}

