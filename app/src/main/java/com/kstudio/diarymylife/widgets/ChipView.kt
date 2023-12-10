package com.kstudio.diarymylife.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import com.kstudio.diarymylife.databinding.ViewChipBinding


/**
 * A simple ChipView class for chips in Android. Provides image, text, and listener
 * functionality.
 */
class ChipView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : LinearLayoutCompat(context, attrs, defStyle) {
    private var binding = ViewChipBinding.inflate(LayoutInflater.from(context), this, true)

    private var onClickCloseIcon: () -> Unit = {}

    init {
        binding.chipClose.setOnClickListener { onClickCloseIcon.invoke() }
    }

    var text: String
        get() {
            return binding.chipText.text.toString()
        }
        set(value) {
            binding.chipText.text = value
        }

    fun setImageChipIcon(image: Int) {
        binding.chipImage.setImageResource(image)
    }

    fun setOnClickCloseIcon(event: () -> Unit) {
        onClickCloseIcon = event
    }

    fun setChipBackgroundColor(color: Int) {
        binding.view.backgroundTintList = ContextCompat.getColorStateList(context, color)
    }
}