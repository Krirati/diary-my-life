package com.kstudio.diarymylife.widgets.custom_switch

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.CustomSwitchBinding

class CustomSwitch @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    private val parentView: View = inflate(context, R.layout.custom_switch, this)

    private val binding: CustomSwitchBinding by lazy { CustomSwitchBinding.bind(parentView) }

    init {
        setup(attrs, defStyleAttr)
    }

    private fun setup(attrs: AttributeSet?, defStyleAttr: Int) {
        val attribute =
            context.obtainStyledAttributes(attrs, R.styleable.CustomSwitch, defStyleAttr, 0)

        binding.apply {
            layoutSwitch.isActivated =
                attribute.getBoolean(R.styleable.CustomSwitch_custom_switch_state, false)
            switchEnable.apply {
                text = attribute.getText(R.styleable.CustomSwitch_custom_switch_title)
                isEnabled =
                    attribute.getBoolean(R.styleable.CustomSwitch_custom_switch_enabled, false)
                isChecked = attribute.getBoolean(
                    R.styleable.CustomSwitch_custom_switch_checked,
                    false
                )


            }
            subtitle.apply {
                text =
                    attribute.getText(R.styleable.CustomSwitch_custom_switch_subtitle)
                isEnabled =
                    attribute.getBoolean(R.styleable.CustomSwitch_custom_switch_enabled, false)
            }
        }
        attribute.recycle()
    }

    fun setStateEnable(isEnable: Boolean) {
        binding.apply {
            layoutSwitch.isActivated = isEnable
            switchEnable.apply {
                isEnabled = isEnable
                if (!isEnable) isChecked = false
            }
            subtitle.isEnabled = isEnable
        }
    }

    fun setStateCheck(isChecked: Boolean) {
        binding.switchEnable.isChecked = isChecked
    }

    fun setOnSwitchCheckChange(listener: (Boolean) -> Unit) {
        binding.switchEnable.setOnCheckedChangeListener { _, isCheck -> listener(isCheck) }
    }

    fun setOnClickWidget(listener: () -> Unit) {
        binding.root.setOnClickListener { listener() }
    }

    fun setSubtitle(text: String = "") {
        binding.subtitle.text = text
    }

    fun isStateCheck(): Boolean {
        return binding.switchEnable.isChecked
    }
}