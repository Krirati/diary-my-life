package com.kstudio.diarymylife.widgets.custom_card_swipe

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.database.model.ActivityEvent
import com.kstudio.diarymylife.databinding.CustomCardSwipeBinding
import com.kstudio.diarymylife.ui.base.swipe_event.SwipeState
import com.kstudio.diarymylife.widgets.CircleImageView
import java.time.LocalDateTime

@SuppressLint("ClickableViewAccessibility")
class CustomCardSwipe @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val parentView: View = inflate(context, R.layout.custom_card_swipe, this)
    private val binding: CustomCardSwipeBinding by lazy { CustomCardSwipeBinding.bind(parentView) }

    private val size: Point = Point()
    private val windowManager: WindowManager =
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var cardViewLeading: Float
    private var cardViewLeadEdge: Float
    private var cardViewTrailEdge: Float
    private var cardViewTrailing: Float
    private var dXLead: Float = 0.toFloat()
    private var dXTrail: Float = 0.toFloat()
    private var previousEvent: Pair<Int?, Int?> = Pair(null, null)
    var state: SwipeState = SwipeState.NONE
    private val swipeState: SwipeState = SwipeState.LEFT_RIGHT

    private var onClickActionListener: () -> Unit? = {}
    private var onClickWidgetListener: () -> Unit? = {}

    init {
        val display = windowManager.defaultDisplay
        display.getSize(size)
        cardViewLeading = size.x.toFloat() * 0.04f
        cardViewLeadEdge = size.x.toFloat() * 0.20f // start position when card swipe to right
        cardViewTrailEdge = size.x.toFloat() * 0.96f // trailing_rubber
        cardViewTrailing = size.x.toFloat() * 0.90f // trailing

        /* On Touch Swipe */
        binding.apply {
            cardDetailContainer.apply {
                setOnClickListener {
                    if (previousEvent.first == MotionEvent.ACTION_MOVE && previousEvent.second == MotionEvent.ACTION_UP) return@setOnClickListener
                    onClickWidgetListener.invoke()
                }
                setOnTouchListener { view, event -> handlerOnTouchEvent(view, event, swipeState) }
            }
            cardAction.setOnClickListener { onClickActionListener.invoke() }
        }
    }

    fun setTitleAndDate(title: String, detail: String, date: LocalDateTime) {
        binding.cardTitle.text = title
        binding.cardDetail.text = detail
        binding.cardDate.bindView(date)
    }

    fun setBackgroundAndKeepPadding(backgroundDrawable: Int) {
        val drawablePadding = Rect()
        val top = binding.cardDetailContainer.paddingTop + drawablePadding.top
        val left = binding.cardDetailContainer.paddingLeft + drawablePadding.left
        val right = binding.cardDetailContainer.paddingRight + drawablePadding.right
        val bottom = binding.cardDetailContainer.paddingBottom + drawablePadding.bottom
        binding.cardDetailContainer.setBackgroundResource(backgroundDrawable)
        binding.cardDetailContainer.setPadding(left, top, right, bottom)
    }

    fun setPillTag(value: String, colorInt: Int) {
        binding.pill.bindView(value, colorInt)
    }

    fun setOnClickWidget(onClick: () -> Unit) {
        this.onClickWidgetListener = onClick
    }

    fun setOnClickAction(onClick: () -> Unit) {
        this.onClickActionListener = onClick
    }

    fun setActivityEvent(activityEvent: List<ActivityEvent>?) {
        binding.activitySection.removeAllViews()
        if (activityEvent != null) {
            binding.activitySection.isVisible = true
            activityEvent.forEach {
                val icon = createChip(it.activityImage)
                binding.activitySection.addView(icon)
            }
        }
    }

    private fun createChip(icon: Int): CircleImageView {
        return CircleImageView(context).apply {
            setIconImage(icon)
        }
    }

    private fun onAnimate(view: View, dx: Float, duration: Long = 100) {
        if (previousEvent.first == MotionEvent.ACTION_DOWN && previousEvent.second == MotionEvent.ACTION_UP || dx < 0) return
        view.animate().x(dx).setDuration(duration).start()
    }

    private fun onSwipeMove(
        currentLead: Float,
        swipeState: SwipeState,
    ): Float {
        return when (swipeState) {
            SwipeState.LEFT, SwipeState.RIGHT, SwipeState.LEFT_RIGHT -> currentLead
            else -> cardViewLeading
        }
    }

    private fun onSwipeUp(swipeState: SwipeState): Float {
        return when (swipeState) {
            SwipeState.NONE -> cardViewLeading
            SwipeState.LEFT -> cardViewLeading
            SwipeState.RIGHT -> cardViewLeadEdge
            else -> cardViewLeading
        }
    }

    private fun getSwipeState(
        currentLead: Float,
        currentTrail: Float,
        swipeState: SwipeState
    ): SwipeState {
        return when {
            swipeState == SwipeState.LEFT && currentLead < cardViewLeading && currentTrail < cardViewTrailEdge -> SwipeState.LEFT
            swipeState == SwipeState.RIGHT && currentLead > cardViewLeadEdge && currentTrail > cardViewTrailing -> SwipeState.RIGHT
            swipeState == SwipeState.LEFT_RIGHT && currentLead < cardViewLeading && currentTrail < cardViewTrailEdge -> SwipeState.LEFT
            swipeState == SwipeState.LEFT_RIGHT && currentLead > cardViewLeadEdge && currentTrail > cardViewTrailing -> SwipeState.RIGHT
            else -> SwipeState.NONE
        }
    }

    private fun handlerOnTouchEvent(
        view: View,
        event: MotionEvent,
        swipeState: SwipeState
    ): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                previousEvent = previousEvent.copy(first = event.action)
                dXLead = view.x - event.rawX
                dXTrail = view.right - event.rawX
                false
            }

            MotionEvent.ACTION_MOVE -> {
                previousEvent = previousEvent.copy(first = event.action)
                onAnimate(view, onSwipeMove(event.rawX + dXLead, swipeState), 0)
                state = getSwipeState(
                    event.rawX + dXLead,
                    event.rawX + dXTrail,
                    swipeState
                )
                false
            }

            MotionEvent.ACTION_UP -> {
                previousEvent = previousEvent.copy(second = event.action)
                onAnimate(view, onSwipeUp(state))
                false
            }

            else -> true
        }
    }
}
