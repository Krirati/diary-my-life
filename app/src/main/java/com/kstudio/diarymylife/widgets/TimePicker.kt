package com.kstudio.diarymylife.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.lifecycle.MutableLiveData
import com.kstudio.diarymylife.databinding.WidgetSelectTimeBinding
import java.time.LocalDateTime
import java.time.LocalTime

class TimePicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    private val binding =
        WidgetSelectTimeBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    companion object {
        const val MAX_HOUR = 23
        const val MAX_MINUTE = 59
        const val MIN_HOUR = 0
        const val MIN_MINUTE = 0
    }

    private var currentHour: Int = LocalDateTime.now().hour
    private var currentMinute: Int = LocalDateTime.now().minute
    private var _localTime: MutableLiveData<LocalTime> = MutableLiveData(LocalTime.now())
    var localTime: MutableLiveData<LocalTime> = _localTime

    init {
        bindView()
    }

    private fun bindView() = with(binding) {
        hour.apply {
            typeNumber.text = "Hour"
            selectNumber.text = String.format("%02d", currentHour)
            buttonPlus.setOnClickListener { plusHour() }
            buttonMinus.setOnClickListener { minusHour() }
        }
        minute.apply {
            typeNumber.text = "Minute"
            selectNumber.text = String.format("%02d", currentMinute)
            buttonPlus.setOnClickListener { plusMinute() }
            buttonMinus.setOnClickListener { minusMinute() }
        }
    }

    fun getCurrentSelectTime(): LocalTime {
        return LocalTime.of(currentHour, currentMinute)
    }

    private fun plusHour() {
        if (MAX_HOUR > currentHour) {
            ++currentHour
            _localTime.postValue(getCurrentSelectTime())
            binding.hour.selectNumber.text = String.format("%02d", currentHour)
            return
        } else {
            currentHour = 0
            binding.hour.selectNumber.text = String.format("%02d", currentHour)
        }
    }

    private fun plusMinute() {
        if (MAX_MINUTE > currentMinute) {
            ++currentMinute
            _localTime.postValue(getCurrentSelectTime())
            binding.minute.selectNumber.text = String.format("%02d", currentMinute)
            return
        } else {
            currentMinute = 0
            binding.minute.selectNumber.text = String.format("%02d", currentMinute)
        }
    }

    private fun minusHour() {
        if (MIN_HOUR < currentHour) {
            --currentHour
            _localTime.postValue(getCurrentSelectTime())
            binding.hour.selectNumber.text = String.format("%02d", currentHour)
            return
        } else {
            currentHour = MAX_HOUR
            binding.hour.selectNumber.text = String.format("%02d", currentHour)
        }
    }

    private fun minusMinute() {
        if (MIN_MINUTE < currentMinute) {
            --currentMinute
            _localTime.postValue(getCurrentSelectTime())
            binding.minute.selectNumber.text = String.format("%02d", currentMinute)
            return
        } else {
            currentMinute = MAX_MINUTE
            binding.minute.selectNumber.text = String.format("%02d", currentMinute)
        }
    }
}