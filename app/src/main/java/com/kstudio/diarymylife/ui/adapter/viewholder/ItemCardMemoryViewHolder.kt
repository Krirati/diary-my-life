package com.kstudio.diarymylife.ui.adapter.viewholder

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.view.Display
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.databinding.ItemCardEventBinding
import com.kstudio.diarymylife.model.JournalItem
import com.kstudio.diarymylife.ui.adapter.ActivityListAdapter
import com.kstudio.diarymylife.ui.base.SwipeEvent.SwipeState
import com.kstudio.diarymylife.utils.convertTime
import java.time.format.DateTimeFormatter

class ItemCardMemoryViewHolder(
    val binding: ItemCardEventBinding,
    context: Context,
    private val navigateToDetail: (Long?) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {


    /** On Swipe */
    private val size: Point = Point()
    private val display: Display
    private val windowManager: WindowManager =
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private val cardViewLeading: Float
    private val cardViewLeadEdge: Float
    private val cardViewTrailEdge: Float
    private val cardViewTrailing: Float
    private var dXLead: Float = 0.toFloat()
    private var dXTrail: Float = 0.toFloat()
    private var previousEvent: Pair<Int?, Int?> = Pair(null, null)

    private val adapterActivity by lazy { ActivityListAdapter(context) }

    init {
        display =
            windowManager.defaultDisplay //activity.getWindowManager().getDefaultDisplay()
        display.getSize(size)
        cardViewLeading = size.x.toFloat() * 0.15f //leading
        cardViewLeadEdge = size.x.toFloat() * 0.15f //leading_rubber
        cardViewTrailEdge = size.x.toFloat() * 0.75f //trailing_rubber
        cardViewTrailing = size.x.toFloat() * 0.90f //trailing
    }

    @SuppressLint("ClickableViewAccessibility")
    fun bind(
        item: JournalItem,
        swipeState: SwipeState,
        onDelete: (Int) -> Unit,
    ) = with(binding) {
//        val activityAdapter = item.data?.activity?.let { ActivityListAdapter(it) }
//        item.data?.activity?.let { adapterActivity.updateActivityItems(it.) }

        journalTitle.text = item.data?.title
        journalDesc.text = item.data?.desc
        journalTime.text = item.data?.timestamp?.let { convertTime(it, "HH:mm") }
        journalDay.text = item.data?.timestamp?.format(DateTimeFormatter.ofPattern("EEEE"))
        journalMonth.text = item.data?.timestamp?.format(DateTimeFormatter.ofPattern("MMMM, dd"))
        journalActivity.apply {
            if (item.data?.activity.isNullOrEmpty()) this.visibility = View.GONE
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
            isNestedScrollingEnabled = false
            adapter = adapterActivity
        }

        buttonDelete.setOnClickListener {
            onDelete(adapterPosition)
        }

        /* On Touch Swipe */
        journalCard.apply {
            setOnClickListener {
                if (previousEvent.first == MotionEvent.ACTION_MOVE && previousEvent.second == MotionEvent.ACTION_UP) return@setOnClickListener
                navigateToDetail(item.data?.journalId)
            }
            setOnTouchListener { view, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        previousEvent = previousEvent.copy(first = event.action)
                        dXLead = view.x - event.rawX
                        dXTrail = view.right - event.rawX
                        false
                    }
                    MotionEvent.ACTION_MOVE -> {
                        previousEvent = previousEvent.copy(first = event.action)
                        onAnimate(view, onSwipeMove(event.rawX + dXLead, swipeState), 0)
                        item.data?.state =
                            getSwipeState(
                                event.rawX + dXLead,
                                event.rawX + dXTrail,
                                swipeState
                            )
                        false
                    }
                    MotionEvent.ACTION_UP -> {
                        previousEvent = previousEvent.copy(second = event.action)
                        item.data?.let { onSwipeUp(it.state) }?.let { onAnimate(view, it) }
                        false
                    }
                    else -> true
                }
            }
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
            SwipeState.LEFT, SwipeState.RIGHT, SwipeState.LEFT_RIGHT -> {
                currentLead
            }
            else -> {
                cardViewLeading
            }
        }
    }

    private fun onSwipeUp(swipeState: SwipeState): Float {
        return when (swipeState) {
            SwipeState.NONE -> cardViewLeading
            SwipeState.LEFT -> 0F
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
            swipeState == SwipeState.LEFT && currentLead < cardViewLeading && currentTrail < cardViewTrailEdge -> {
                SwipeState.LEFT
            }
            swipeState == SwipeState.RIGHT && currentLead > cardViewLeadEdge && currentTrail > cardViewTrailing -> {
                SwipeState.RIGHT
            }
            swipeState == SwipeState.LEFT_RIGHT && currentLead < cardViewLeading && currentTrail < cardViewTrailEdge -> {

                SwipeState.LEFT
            }
            swipeState == SwipeState.LEFT_RIGHT && currentLead > cardViewLeadEdge && currentTrail > cardViewTrailing -> {
                SwipeState.RIGHT
            }
            else -> {
                SwipeState.NONE
            }
        }
    }
}