package com.kstudio.diarymylife.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.WidgetAppBarBinding

class AppBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : LinearLayoutCompat(context, attrs, defStyle) {
    private val parentView: View = inflate(context, R.layout.widget_app_bar, this)

    private val binding: WidgetAppBarBinding by lazy { WidgetAppBarBinding.bind(parentView) }

    fun bindView(title: String, onBack: () -> Unit) {
        binding.apply {
            this.appBarTitle.text = title
            this.backButton.setOnClickListener { onBack.invoke() }
        }
    }
}
