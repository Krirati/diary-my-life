package com.kstudio.diarymylife.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@SuppressLint("SimpleDateFormat")
fun convertTime(timestamp: LocalDateTime, format: String): String {
    val formatter = DateTimeFormatter.ofPattern(format)
    return timestamp.format(formatter)
}

fun Date.toStringFormat(stringFormat: String = "dd-MM-yyyy"): String {
    val dateFormat = SimpleDateFormat(stringFormat, Locale.getDefault())
    return try {
        dateFormat.format(this)
    } catch (e: ParseException) {
        e.printStackTrace()
        "N/A"
    }
}

fun String.toDate(stringFormat: String = "dd-MM-yyyy"): Date? {
    val format = SimpleDateFormat(stringFormat, Locale.US)
    return try {
        format.parse(this)
    } catch (e: ParseException) {
        e.printStackTrace()
        null
    }
}

fun String.toLocalDate(stringFormat: String = "dd-MM-yyyy"): LocalDate? {
    val formatter = DateTimeFormatter.ofPattern(stringFormat)
    return LocalDate.parse(this, formatter)
}

fun LocalDate.toStringFormatApp(stringFormat: String = "MMMM, dd EEEE"): String? {
    val formatter = DateTimeFormatter.ofPattern(stringFormat)
    return this.format(formatter)
}


fun getFormatDate(day: Int, month: Int, year: Int): String {
    val twoDigit = "%1$02d" // two digits
    val twoDigitDay = String.format(twoDigit, day)
    val twoDigitMonth = String.format(twoDigit, month)
    return "${twoDigitDay}-${twoDigitMonth}-${year}"
}