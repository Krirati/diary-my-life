package com.kstudio.diarymylife.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.kstudio.diarymylife.databinding.CustomPillViewBinding

class PillView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    private val binding =
        CustomPillViewBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    @SuppressLint("ResourceType")
    fun bindView(value: String, @DrawableRes color: Int) = with(binding) {
        if (value.isNotBlank()) root.visibility = View.VISIBLE
        text.text = value
        container.backgroundTintList = ContextCompat.getColorStateList(context, color)
    }
}
