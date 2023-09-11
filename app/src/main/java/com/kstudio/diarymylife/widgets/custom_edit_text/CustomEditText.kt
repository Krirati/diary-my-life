package com.kstudio.diarymylife.widgets.custom_edit_text

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import androidx.constraintlayout.widget.ConstraintLayout
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.CustomEditTextBinding

class CustomEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyle, defStyleRes) {

    private val parentView: View = inflate(context, R.layout.custom_edit_text, this)
    private val binding: CustomEditTextBinding by lazy { CustomEditTextBinding.bind(parentView) }

    init {
        setup(attrs, defStyle)
        setOnFocusChange()
    }

    private fun setup(attrs: AttributeSet?, defStyle: Int) {
        val attribute =
            context.obtainStyledAttributes(attrs, R.styleable.CustomEditText, defStyle, 0)

        binding.apply {
            title.apply {
                text = attribute.getText(R.styleable.CustomEditText_custom_edit_text_title)
                visibility = View.GONE
            }
            placeholder.text =
                attribute.getText(R.styleable.CustomEditText_custom_edit_text_placeholder)
            editText.apply {
                visibility = if (attribute.getBoolean(
                        R.styleable.CustomEditText_custom_edit_action_custom,
                        false
                    )
                ) {
                    View.GONE
                } else View.VISIBLE
                maxLines = attribute.getInt(R.styleable.CustomEditText_custom_edit_text_max_line, 1)
            }
        }
        attribute.recycle()
    }


    private fun setOnFocusChange() = with(binding) {
        editText.apply {
            onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
                when (hasFocus) {
                    true -> {
                        title.visibility = View.VISIBLE
                        placeholder.visibility = View.GONE
                    }

                    false -> {
                        title.visibility =
                            if (this.text.isNullOrEmpty()) View.GONE else View.VISIBLE
                        placeholder.visibility =
                            if (this.text.isNullOrEmpty()) View.VISIBLE else View.GONE
                    }
                }
            }
        }
    }

    fun setOnClickWidget(listener: () -> Unit) {
        binding.placeholder.setOnClickListener { listener() }
    }

    fun setOnKeyListener(listener: () -> Unit) {
        binding.editText.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                listener()
                true
            }
            false
        }
    }

    fun setOnTextChange(
        onTextChanged: (s: CharSequence?, start: Int, before: Int, count: Int) -> Unit,
    ) {
        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                /*DO NOT ANYTHING*/
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onTextChanged(s, start, before, count)
            }

            override fun afterTextChanged(s: Editable?) { /*DO NOT ANYTHING*/
            }
        })
    }

    fun setDefaultTextValue(value: String) = with(binding) {
        title.visibility = if (value.isEmpty()) View.GONE else View.VISIBLE
        placeholder.visibility = if (value.isEmpty()) View.VISIBLE else View.GONE
        editText.setText(value)
    }
}