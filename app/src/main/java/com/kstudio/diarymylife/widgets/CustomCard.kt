package com.kstudio.diarymylife.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.ItemCardBinding
import com.kstudio.diarymylife.utils.BackgroundTheme

class CustomCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyle, defStyleRes) {

    private val parentView: View = inflate(context, R.layout.item_card, this)

    private val binding: ItemCardBinding by lazy { ItemCardBinding.bind(parentView) }

    init {
        setup(attrs, defStyle)
    }

    private fun setup(attrs: AttributeSet?, defStyle: Int) {
        val attribute =
            context.obtainStyledAttributes(attrs, R.styleable.CustomCard, defStyle, 0)
        val background = attribute.getInt(R.styleable.CustomCard_custom_card_background_color, 6)
        binding.root.setBackgroundResource(BackgroundTheme().mapperThemeCard(background))

        attribute.recycle()
    }

    fun bind(
        cardTitle: String,
        cardValue: Int,
        @DrawableRes cardIcon: Int?,
    ) = with(binding) {
        title.text = cardTitle
        value.text = cardValue.toString()
        cardIcon?.let { icon.setImageResource(it) }
    }

    fun setValue(cardValue: Long) = with(binding) {
        value.text = cardValue.toString()
    }
}