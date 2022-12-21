package com.kstudio.diarymylife.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.ItemCardSettingBinding

class CardSetting @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyle, defStyleRes) {

    private val parentView: View = inflate(context, R.layout.item_card_setting, this)

    private val binding: ItemCardSettingBinding by lazy { ItemCardSettingBinding.bind(parentView) }

    fun setTitle(value: String) = with(binding) {
        cardName.text = value
    }

    fun setIcon(@DrawableRes icon: Int) = with(binding) {
        cardImage.setImageResource(icon)
    }

    fun onWidgetClick(listener: (View) -> Unit): CardSetting {
        binding.activityContainer.setOnClickListener { listener(this) }
        return this
    }
}