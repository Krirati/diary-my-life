package com.kstudio.diarymylife.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.kstudio.diarymylife.databinding.ItemDateTimeBinding
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("ViewConstructor")
class DateView constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    private var binding = ItemDateTimeBinding.inflate(
        LayoutInflater.from(context),this, true
    )

    fun bindView(time: Date) {
        with(binding) {
            day.text = SimpleDateFormat("EEEE", Locale.ENGLISH).format(Date((time.time)))
            month.text = SimpleDateFormat("MMMM, dd", Locale.ENGLISH).format(Date((time.time)))
        }
    }
}