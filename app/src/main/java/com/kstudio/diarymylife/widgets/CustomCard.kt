package com.kstudio.diarymylife.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.ItemCardBinding

class CustomCard @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val parentView: View = inflate(context, R.layout.item_card, this)

    private val binding: ItemCardBinding by lazy { ItemCardBinding.bind(parentView) }

    fun bind(cardTitle: String, cardValue: Int, @DrawableRes cardIcon: Int?) = with(binding) {
        title.text = cardTitle
        value.text = cardValue.toString()
        cardIcon?.let { icon.setImageResource(it) }
    }

    fun setValue(cardValue: Int) = with(binding){
        value.text = cardValue.toString()
    }
}