package com.kstudio.diarymylife.widgets.custom_chart

import android.content.Context
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.kstudio.diarymylife.R


class CustomChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyle, defStyleRes) {

    private var mMaxValue = 0f
    private var mBarWidth = 0
    private var mBarHeight = 0
    private var mEmptyColor = 0
    private var mProgressColor = 0
    private var mProgressClickColor = 0
    private var mBarRadius = 0
    private var mPinTextColor = 0
    private var mPinBackgroundColor = 0
    private var mPinPaddingTop = 0
    private var mPinPaddingBottom = 0
    private var mPinPaddingEnd = 0
    private var mPinPaddingStart = 0
    private var mBarTitleColor = 0
    private var mBarTitleTxtSize = 0f
    private var mPinTxtSize = 0f

    //    private var mMetrics: DisplayMetrics? = null
//    private var oldFrameLayout: FrameLayout? = null
    private var isBarCanBeClick = false
    private var mDataList: ArrayList<BarData>? = null

    //    private var isOldBarClicked = false
//    private var isBarsEmpty = false
    private var mPinMarginTop = 0
    private var mPinMarginBottom = 0
    private var mPinMarginEnd = 0
    private var mPinMarginStart = 0
    private var mPinDrawable = 0

    //    private var pins = ArrayList<TextView>()
    private var mBarTitleMarginTop = 0
    private var mBarTitleSelectedColor = 0
    private var mProgressDisableColor = 0

    //    private val listener: OnBarClickedListener? = null
    private var mBarCanBeToggle = false

    init {
        if (attrs != null) {
            setAttrs(attrs, defStyle)
        }
    }

    private fun setAttrs(attr: AttributeSet, defStyle: Int) {
        val typedArray = context.obtainStyledAttributes(attr, R.styleable.CustomChart, defStyle, 0)
        mBarWidth = typedArray.getDimensionPixelSize(R.styleable.CustomChart_custom_bar_width, 0)
        mBarHeight = typedArray.getDimensionPixelSize(R.styleable.CustomChart_custom_bar_height, 0)
        mBarRadius = typedArray.getDimensionPixelSize(R.styleable.CustomChart_custom_bar_radius, 0)
        mEmptyColor = typedArray.getResourceId(
            R.styleable.CustomChart_custom_empty_color,
            ContextCompat.getColor(context, R.color.secondary_grey50)
        )
        mProgressColor = typedArray.getResourceId(
            R.styleable.CustomChart_custom_progress_color,
            ContextCompat.getColor(context, R.color.amber_600)
        )
        mProgressClickColor = typedArray.getResourceId(
            R.styleable.CustomChart_custom_progress_click_color,
            ContextCompat.getColor(context, R.color.red_700)
        )
        mProgressDisableColor = typedArray.getResourceId(
            R.styleable.CustomChart_custom_progress_disable_color,
            ContextCompat.getColor(context, android.R.color.darker_gray)
        )
        mBarTitleSelectedColor = typedArray.getResourceId(
            R.styleable.CustomChart_custom_bar_title_selected_color,
            ContextCompat.getColor(context, R.color.new_york_pink)
        )
        mPinTextColor = typedArray.getResourceId(
            R.styleable.CustomChart_custom_pin_text_color,
            ContextCompat.getColor(context, R.color.brown_text)
        )
        mPinBackgroundColor = typedArray.getResourceId(
            R.styleable.CustomChart_custom_pin_background_color,
            ContextCompat.getColor(context, R.color.mood_good_drop)
        )
        mPinPaddingTop =
            typedArray.getDimensionPixelSize(R.styleable.CustomChart_custom_pin_padding_top, 3)
        mPinPaddingBottom =
            typedArray.getDimensionPixelSize(R.styleable.CustomChart_custom_pin_padding_bottom, 3)
        mPinPaddingEnd =
            typedArray.getDimensionPixelSize(R.styleable.CustomChart_custom_pin_padding_end, 3)
        mPinPaddingStart =
            typedArray.getDimensionPixelSize(R.styleable.CustomChart_custom_pin_padding_start, 3)
        isBarCanBeClick =
            typedArray.getBoolean(R.styleable.CustomChart_custom_bar_can_be_click, false)
        mBarTitleColor = typedArray.getResourceId(
            R.styleable.CustomChart_custom_bar_title_color,
            ContextCompat.getColor(context, R.color.mood_average_drop)
        )
        mMaxValue = typedArray.getFloat(R.styleable.CustomChart_custom_max_value, 1f)
        mBarTitleTxtSize =
            typedArray.getDimension(R.styleable.CustomChart_custom_bar_title_txt_size, 14f)
        mPinTxtSize = typedArray.getDimension(R.styleable.CustomChart_custom_pin_txt_size, 14f)
        mPinMarginTop =
            typedArray.getDimensionPixelSize(R.styleable.CustomChart_custom_pin_margin_top, 0)
        mPinMarginBottom =
            typedArray.getDimensionPixelSize(R.styleable.CustomChart_custom_pin_margin_bottom, 0)
        mPinMarginEnd =
            typedArray.getDimensionPixelSize(R.styleable.CustomChart_custom_pin_margin_end, 0)
        mPinMarginStart =
            typedArray.getDimensionPixelSize(R.styleable.CustomChart_custom_pin_margin_start, 0)
        mBarTitleMarginTop = typedArray.getDimensionPixelSize(
            R.styleable.CustomChart_custom_bar_title_margin_top,
            0
        )
        mPinDrawable = typedArray.getResourceId(R.styleable.CustomChart_custom_pin_drawable, 0)
        mBarCanBeToggle =
            typedArray.getBoolean(R.styleable.CustomChart_custom_bar_can_be_toggle, false)
        typedArray.recycle()
    }

    fun setDataList(dataList: ArrayList<BarData>) {
        this.mDataList = dataList
    }

    fun setMaxValue(max: Int) {
        mMaxValue = max.toFloat()
    }

    private fun getBar(
        barValue: Float,
        index: Int,
        barColor: Int,
        iconDrawable: Int
    ): FrameLayout {
        val maxValue = mMaxValue * 100

        val linearLayout = LinearLayout(context)
        val params = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )

        params.gravity = Gravity.CENTER
        linearLayout.apply {
            layoutParams = params
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
        }

        //Adding bar
        val bar = ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal)
        val drawable = ContextCompat.getDrawable(context, R.drawable.progress_bar_shape)
        bar.apply {
            progress = barValue.toInt()
            visibility = View.VISIBLE
            isIndeterminate = false
            max = maxValue.toInt()
            progressDrawable = drawable
            val wrappedDrawable = (drawable as LayerDrawable).findDrawableByLayerId(R.id.progress)
            DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(context, barColor))
        }

        val progressParams = LayoutParams(mBarWidth, mBarHeight)
        progressParams.gravity = Gravity.CENTER
        bar.layoutParams = progressParams

        val anim = BarAnimation.newInstance(bar, 0f, barValue)
        anim.duration = 3000
        bar.startAnimation(anim)

        val layerDrawable = bar.progressDrawable
        layerDrawable.mutate()

        linearLayout.addView(bar)

        val imageView = AppCompatImageView(context)
        val imageViewParams = LayoutParams(64, 64)
        imageViewParams.setMargins(0, mBarTitleMarginTop, 0, 0)
        imageView.apply {
            setBackgroundResource(iconDrawable)
            layoutParams = imageViewParams
        }

        linearLayout.addView(imageView)

        val rootFrameLayout = FrameLayout(context)
        val rootParams = LinearLayout.LayoutParams(
            0,
            LayoutParams.MATCH_PARENT,
            1f
        )

        rootParams.gravity = Gravity.CENTER

        rootFrameLayout.layoutParams = rootParams

        rootFrameLayout.addView(linearLayout)

        rootFrameLayout.tag = index
        return rootFrameLayout
    }


    fun build() {
        removeAllViews()

        val linearLayout = LinearLayout(context)
        val params = LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )

        val d = ContextCompat.getDrawable(context, mPinDrawable)
        var h = d?.intrinsicHeight

        if (h != null) {
            if (mPinMarginBottom != 0) h += mPinMarginBottom / 2
            linearLayout.setPadding(0, h, 0, 0)
            linearLayout.layoutParams = params
        }

        addView(linearLayout)
        var i = 0
        if (mDataList != null) {
            for (data in mDataList!!) {
                val barValue = data.barValue * 100
                val bar = getBar(barValue, i, data.barColor, data.iconDrawable)
                linearLayout.addView(bar)
                i++
            }
        }

        viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }
}
