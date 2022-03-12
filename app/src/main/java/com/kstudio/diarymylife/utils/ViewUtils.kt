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

@SuppressLint("SimpleDateFormat")
fun convertTime(timestamp: LocalDate, format: String): String {
    val formatter = DateTimeFormatter.ofPattern(format)
    return timestamp.format(formatter)
}

fun convertDateToString(timestamp: Date): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    return LocalDate.parse(timestamp.toString(), formatter);
}

@SuppressLint("SimpleDateFormat")
fun compareTime(previous: Date?, current: Date): Boolean {
    if (previous == null) return false
    val fmt = SimpleDateFormat("yyyyMMdd")
    return fmt.format(previous).equals(fmt.format(current))
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


fun getFormatDate(day: Int, month: Int, year: Int): String {
    val twoDigit = "%1$02d" // two digits
    val twoDigitDay = String.format(twoDigit, day)
    val twoDigitMonth = String.format(twoDigit, month)
    return "${twoDigitDay}-${twoDigitMonth}-${year}"
}