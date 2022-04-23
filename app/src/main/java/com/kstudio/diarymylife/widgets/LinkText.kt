package com.kstudio.diarymylife.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView
import com.kstudio.diarymylife.R

class LinkText : AppCompatTextView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var isDuplicateColor: Boolean = false
    private var textForSplit: String = ""

    fun setTextForDuplicationColor(isDuplicate: Boolean, splitBy: String) {
        isDuplicateColor = isDuplicate
        textForSplit = splitBy
        if (isDuplicateColor) {
            val content = SpannableString(text)
            content.setSpan(
                ForegroundColorSpan(context.getColor(R.color.grey)),
                content.indexOf(textForSplit),
                content.length,
                0
            )
            text = content
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                val content = SpannableString(text)
                if (isDuplicateColor) {
                    content.setSpan(
                        UnderlineSpan(),
                        content.indexOf(textForSplit),
                        content.length,
                        0
                    )
                    content.setSpan(
                        ForegroundColorSpan(Color.GRAY),
                        content.indexOf(textForSplit),
                        content.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                } else {
                    content.setSpan(UnderlineSpan(), 0, content.length, 0)
                }
                text = content
            }
            MotionEvent.ACTION_CANCEL,
            MotionEvent.ACTION_UP -> {
                val content = SpannableString(text.toString())
                if (isDuplicateColor) content.setSpan(
                    ForegroundColorSpan(Color.GRAY),
                    content.indexOf(textForSplit),
                    content.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                text = content
            }
        }
        return super.onTouchEvent(event)
    }

}