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
        const val NEW_YORK_PINK = 8
        const val PEWTER_BLUE = 7
        const val BEIGE = 6
        const val LAUREL_GREEN = 5
        const val PALE_SPRINT_BUD = 4
        const val LEMON_YELLOW_CRAYOLA = 3
        const val DEEP_CHAMPAGNE = 2
        const val MACARONI_AND_CHEESE = 1
        const val SANDY_BROWN = 0
    }

    fun mapperThemeCard(background: Int): Int {
        return when (background) {
            BEIGE -> R.drawable.bg_round_beige
            LAUREL_GREEN -> R.drawable.bg_round_laurel_green
            PALE_SPRINT_BUD -> R.drawable.bg_round_pale_sprint_bud
            LEMON_YELLOW_CRAYOLA -> R.drawable.bg_round_lemon_yellow_crayola
            DEEP_CHAMPAGNE -> R.drawable.bg_round_deep_champagne
            SANDY_BROWN -> R.drawable.bg_round_sandy_brown
            MACARONI_AND_CHEESE -> R.drawable.bg_round_macaroni_and_cheese
            PEWTER_BLUE -> R.drawable.bg_round_pewter_blue
            NEW_YORK_PINK -> R.drawable.bg_round_new_york_pink
            else -> R.drawable.bg_round_card_action
        }
    }
}

