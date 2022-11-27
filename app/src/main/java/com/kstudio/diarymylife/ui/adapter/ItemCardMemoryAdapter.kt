package com.kstudio.diarymylife.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.databinding.ItemCardAddBinding
import com.kstudio.diarymylife.databinding.ItemCardEventBinding
import com.kstudio.diarymylife.data.MoodItem
import com.kstudio.diarymylife.ui.base.SwipeEvent.SwipeState
import com.kstudio.diarymylife.ui.adapter.viewholder.ItemAddViewHolder
import com.kstudio.diarymylife.ui.adapter.viewholder.ItemCardMemoryViewHolder

class ItemCardMemoryAdapter(
    private val memoryItems: List<MoodItem>,
    private val onNavigateToDetail: (Long?) -> Unit,
    private val onDeleted: (Long?) -> Unit?,
    private val onAddItem: () -> Unit,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val swipeState: SwipeState = SwipeState.LEFT_RIGHT

    companion object {
        const val VIEW_ITEM = 11
        const val VIEW_ADD = 10
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_ITEM -> ItemCardMemoryViewHolder(
                ItemCardEventBinding.inflate(inflater, parent, false),
                context = parent.context,
                onNavigateToDetail,
            )
            else -> ItemAddViewHolder(
                ItemCardAddBinding.inflate(inflater, parent, false),
                onAddItem
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_ITEM -> {
                (holder as ItemCardMemoryViewHolder).bind(
                    item = memoryItems[position],
                    swipeState = swipeState,
                    onDelete = ::deleteItem,
                )
            }
            VIEW_ADD -> (holder as ItemAddViewHolder).bind()
        }
    }

    override fun getItemCount(): Int = memoryItems.size

    override fun getItemViewType(position: Int): Int {
        return memoryItems[position].viewType
    }

    private fun deleteItem(position: Int) {
        onDeleted(memoryItems[position].data?.moodId)
        memoryItems.drop(position)
        notifyItemRangeChanged(position, memoryItems.size)
    }
}