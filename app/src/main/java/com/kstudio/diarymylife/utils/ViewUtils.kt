package com.kstudio.diarymylife.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@SuppressLint("SimpleDateFormat")
fun convertTime(timestamp: LocalDateTime, format: String): String {
    val formatter = DateTimeFormatter.ofPattern(format)
    return timestamp.format(formatter)
}

@SuppressLint("SimpleDateFormat")
fun compareTime(previous: Date?, current: Date): Boolean {
    if (previous == null) return false
    val fmt = SimpleDateFormat("yyyyMMdd")
    return fmt.format(previous).equals(fmt.format(current))
}
