package com.kstudio.diarymylife.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.kstudio.diarymylife.databinding.ViewCircleImageBinding

class CircleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : FrameLayout(context, attrs) {
    private var binding = ViewCircleImageBinding.inflate(LayoutInflater.from(context), this, true)

    fun setIconImage(image:Int) {
        binding.image.setImageResource(image)
    }
}