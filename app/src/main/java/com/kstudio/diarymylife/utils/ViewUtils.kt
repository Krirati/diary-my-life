package com.kstudio.diarymylife.utils

import android.annotation.SuppressLint
import android.util.DisplayMetrics
import com.kstudio.diarymylife.utils.Formats.Companion.DATE_FORMAT
import com.kstudio.diarymylife.utils.Formats.Companion.DATE_FORMAT_APP
import com.kstudio.diarymylife.utils.Formats.Companion.DATE_TIME_FORMAT_APP
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@SuppressLint("SimpleDateFormat")
fun convertTime(timestamp: LocalDateTime, format: String = DATE_TIME_FORMAT_APP): String {
    val formatter = DateTimeFormatter.ofPattern(format)
    return timestamp.format(formatter)
}

fun Date.toStringFormat(stringFormat: String = DATE_FORMAT): String {
    val dateFormat = SimpleDateFormat(stringFormat, Locale.getDefault())
    return try {
        dateFormat.format(this)
    } catch (e: ParseException) {
        e.printStackTrace()
        "N/A"
    }
}

fun LocalDate.toStringFormat(stringFormat: String = DATE_FORMAT): String {
    val formatter = DateTimeFormatter.ofPattern(stringFormat)
    return this.format(formatter)
}

fun String.toDate(stringFormat: String = DATE_FORMAT): Date? {
    val format = SimpleDateFormat(stringFormat, Locale.US)
    return try {
        format.parse(this)
    } catch (e: ParseException) {
        e.printStackTrace()
        null
    }
}

fun String.toLocalDate(stringFormat: String = DATE_FORMAT): LocalDate? {
    val formatter = DateTimeFormatter.ofPattern(stringFormat)
    return LocalDate.parse(this, formatter)
}

fun LocalDate.toStringFormatApp(stringFormat: String = DATE_FORMAT_APP): String? {
    val formatter = DateTimeFormatter.ofPattern(stringFormat)
    return this.format(formatter)
}

fun getFormatDate(day: Int, month: Int, year: Int): String {
    val twoDigit = "%1$02d" // two digits
    val twoDigitDay = String.format(twoDigit, day)
    val twoDigitMonth = String.format(twoDigit, month)
    return "${twoDigitDay}-${twoDigitMonth}-${year}"
}

fun Int.dpToPx(displayMetrics: DisplayMetrics): Int = (this * displayMetrics.density).toInt()

