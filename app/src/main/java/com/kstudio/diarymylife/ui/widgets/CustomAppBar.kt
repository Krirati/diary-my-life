package com.kstudio.diarymylife.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.CustomAppBarBinding

class CustomAppBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    private val parentView: View = inflate(context, R.layout.custom_app_bar, this)

    private val binding: CustomAppBarBinding by lazy { CustomAppBarBinding.bind(parentView) }

    fun setTitlePage(title: String): CustomAppBar {
        binding.titleCustomText.text = title
        return this
    }

    fun setBackButton(listener: OnClickListener): CustomAppBar {
        with(binding.titleCustomIconBack) {
            isVisible = true
            setOnClickListener(listener)
        }
        return this
    }
}