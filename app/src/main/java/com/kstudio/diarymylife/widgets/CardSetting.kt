package com.kstudio.diarymylife.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.ItemCardSettingBinding

class CardSetting constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val parentView: View = inflate(context, R.layout.item_card_setting, this)

    private val binding: ItemCardSettingBinding by lazy { ItemCardSettingBinding.bind(parentView) }

    fun setTitle(value: String) = with(binding) {
        cardName.text = value
    }

    fun setIcon(@DrawableRes icon: Int) = with(binding) {
        cardImage.setImageResource(icon)
    }
}