package com.kstudio.diarymylife.widgets.custom_chart

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class BarData(
    val barValue: Float,
    @ColorRes val barColor: Int,
    @DrawableRes val iconDrawable: Int
)
