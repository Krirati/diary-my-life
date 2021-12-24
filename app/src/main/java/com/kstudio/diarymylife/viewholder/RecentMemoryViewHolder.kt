package com.kstudio.diarymylife.viewholder

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.view.Display
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.adapter.ActivityListAdapter
import com.kstudio.diarymylife.databinding.ItemRecentEventBinding
import com.kstudio.diarymylife.model.JournalCard
import com.kstudio.diarymylife.ui.base.SwipeEvent.SwipeState
import com.kstudio.diarymylife.utils.DateView
import com.kstudio.diarymylife.utils.compareTime
import com.kstudio.diarymylife.utils.convertTime
import java.util.*

class RecentMemoryViewHolder(
    val binding: ItemRecentEventBinding,
    private val context: Context,
    private val callback: (String) -> Unit,
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
        item: JournalCard, swipeState: SwipeState, previousTime: Date?, onDelete: (Int) -> Unit
    ) = with(binding) {
        val activityAdapter = item.activity?.let { ActivityListAdapter(it) }

        if (!compareTime(previousTime, item.timestamp)) {
            val dateView = DateView(context)
            dateView.bindView(item.timestamp)
            binding.timeContainer.addView(dateView)
        }

        journalCard.setOnClickListener {}
        journalTitle.text = item.title
        journalDesc.text = item.desc
        journalTime.text = convertTime(item.timestamp)
        journalActivity.apply {
            layoutManager = GridLayoutManager(
                context,
                1,
                GridLayoutManager.HORIZONTAL,
                false
            )
            adapter = activityAdapter
        }

        buttonDelete.setOnClickListener {
            onDelete(adapterPosition)
        }

        /* On Touch Swipe */
        journalCard.apply {
            setOnClickListener {
                callback(item.journalId)
            }
//            setOnTouchListener { view, event ->
//                when (event.action) {
//                    MotionEvent.ACTION_DOWN -> {
//                        dXLead = view.x - event.rawX
//                        dXTrail = view.right - event.rawX
//                        false
//                    }
//                    MotionEvent.ACTION_MOVE -> {
//                        onAnimate(view, onSwipeMove(event.rawX + dXLead, swipeState), 0)
//                        item.state =
//                            getSwipeState(
//                                event.rawX + dXLead,
//                                event.rawX + dXTrail,
//                                swipeState
//                            )
//
//                        true
//                    }
//                    MotionEvent.ACTION_UP -> {
//                        onAnimate(view, onSwipeUp(item.state), 250)
//                        false
//                    }
//                    else -> false
//                }
//            }
        }
    }


    private fun onAnimate(view: View, dx: Float, duration: Long) {
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