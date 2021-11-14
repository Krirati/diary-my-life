package com.kstudio.diarymylife.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.viewbinding.ViewBinding
import com.kstudio.diarymylife.R
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

    private val binding = ItemDateTimeBinding.inflate(LayoutInflater.from(context))

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.item_date_time, this, true)
        orientation = HORIZONTAL
    }

    fun bindView(time: Date) {
        with(binding) {
            day.text = SimpleDateFormat("EEEE", Locale.ENGLISH).format(Date((time.time)))
            mouth.text = SimpleDateFormat("MMM-dd", Locale.ENGLISH).format(Date((time.time)))
        }
    }
}