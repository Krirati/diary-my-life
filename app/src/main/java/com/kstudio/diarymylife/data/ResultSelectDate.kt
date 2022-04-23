package com.kstudio.diarymylife.data

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class ResultSelectDate(
    var day: LocalDate?,
    var time: LocalTime?
) {
    fun getLocalDateTime(): LocalDateTime {
        return LocalDateTime.of(day, time)
    }
}
