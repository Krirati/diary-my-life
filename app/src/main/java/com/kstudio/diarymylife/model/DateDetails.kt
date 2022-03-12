package com.kstudio.diarymylife.model

import com.kstudio.diarymylife.utils.toStringFormat
import java.util.*

data class DateDetailsUI(
    val dateKey: String,
    val day: Int,
    val dayOfWeek: String,
    val monthOfYear: String,
)

fun Date.toDateDetails(): DateDetailsUI {
    val calendar = GregorianCalendar()
    calendar.time = this

    return DateDetailsUI(
        dateKey = calendar.time.toStringFormat(),
        day = calendar.get(Calendar.DAY_OF_MONTH),
        dayOfWeek = dayOfWeek[calendar.get(Calendar.DAY_OF_WEEK) - 1],
        monthOfYear = monthOfYear[calendar.get(Calendar.MONTH)]
    )
}

val dayOfWeek = listOf(
    "Sunday",
    "Monday",
    "Tuesday",
    "Wednesday",
    "Thursday",
    "Friday",
    "Saturday"
)

val monthOfYear = listOf(
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December",
)
