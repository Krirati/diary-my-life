package com.kstudio.diarymylife.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun convertTime(timestamp: Date): String {
    val sdf = SimpleDateFormat("hh:mm")
    return sdf.format(timestamp)
}

@SuppressLint("SimpleDateFormat")
fun compareTime(previous: Date, current: Date): Boolean {
    val fmt = SimpleDateFormat("yyyyMMdd")
    return fmt.format(previous).equals(fmt.format(current))
}
