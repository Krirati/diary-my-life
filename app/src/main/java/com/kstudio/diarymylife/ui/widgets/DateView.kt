package com.kstudio.diarymylife.ui.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.kstudio.diarymylife.databinding.ItemDateTimeBinding
import com.kstudio.diarymylife.model.DateDetailsUI
import com.kstudio.diarymylife.utils.convertTime
import com.kstudio.diarymylife.utils.toStringFormatApp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@SuppressLint("ViewConstructor")
class DateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    private var binding = ItemDateTimeBinding.inflate(
        LayoutInflater.from(context),this, true
    )

    fun bindView(localDateTime: LocalDateTime) {
        with(binding) {
            day.text = convertTime(localDateTime, "EEEE")
            month.text = convertTime(localDateTime, "MMMM, dd")
            time.text = convertTime(localDateTime, "HH:mm")
        }
    }

    fun bindView(localDateTime: LocalDate) {
        with(binding) {
            day.text = localDateTime.toStringFormatApp("EEEE")
            month.text = localDateTime.toStringFormatApp("MMMM, dd")
        }
    }

    fun bindDate(localDateTime: DateDetailsUI) {
        with(binding) {
            day.text = localDateTime.dayOfWeek
            month.text = localDateTime.monthOfYear + ", "+ localDateTime.day
        }
    }

    fun bindTime(localTime: LocalTime) {
        with(binding) {
            time.text = localTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        }
    }

    fun getLocalDateTime(): LocalDateTime {
//        TODO("get from time that select")
        return LocalDateTime.now()
    }
}