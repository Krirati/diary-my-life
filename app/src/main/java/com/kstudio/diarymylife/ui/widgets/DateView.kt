package com.kstudio.diarymylife.ui.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.kstudio.diarymylife.databinding.ItemDateTimeBinding
import com.kstudio.diarymylife.model.DateDetailsUI
import com.kstudio.diarymylife.utils.convertTime
import java.time.LocalDateTime

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
            time.text = convertTime(localDateTime, "hh:mm")
        }
    }

    fun bindView(localDateTime: DateDetailsUI) {
        with(binding) {
            day.text = localDateTime.dayOfWeek
            month.text = localDateTime.monthOfYear + ", "+ localDateTime.day
            time.text = convertTime(LocalDateTime.now(), "hh:mm")
        }
    }

    fun getLocalDateTime(): LocalDateTime {
//        TODO("get from time that select")
        return LocalDateTime.now()
    }
}