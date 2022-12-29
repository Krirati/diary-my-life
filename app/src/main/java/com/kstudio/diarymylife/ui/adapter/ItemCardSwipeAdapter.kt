package com.kstudio.diarymylife.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.data.MoodItem
import com.kstudio.diarymylife.databinding.ItemCardAddBinding
import com.kstudio.diarymylife.databinding.ItemSwipeCardBinding
import com.kstudio.diarymylife.ui.adapter.viewholder.ItemAddViewHolder
import com.kstudio.diarymylife.ui.adapter.viewholder.ItemCardSwipeViewHolder

class ItemCardSwipeAdapter(
    private val onAddItem: () -> Unit,
    private val onDeleted: (Long?) -> Unit?,
    private val onNavigateToDetail: (Long?) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var moodItems: List<MoodItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ItemCardMemoryAdapter.VIEW_ITEM -> ItemCardSwipeViewHolder(
                ItemSwipeCardBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )
            else -> ItemAddViewHolder(
                ItemCardAddBinding.inflate(inflater, parent, false),
                onAddItem
            )
        }
    }

    override fun getItemCount(): Int = moodItems.size

    override fun getItemViewType(position: Int): Int {
        return moodItems[position].viewType
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ItemCardMemoryAdapter.VIEW_ITEM -> {
                (holder as ItemCardSwipeViewHolder).bind(
                    item = moodItems[position],
                    onActionClickDelete = ::onClickDeleteItem,
                    onClickToDetail = onNavigateToDetail
                )
            }
            ItemCardMemoryAdapter.VIEW_ADD -> (holder as ItemAddViewHolder).bind()
        }
    }

    private fun onClickDeleteItem(position: Int) {
        onDeleted(moodItems[position].data?.moodId)
        moodItems.drop(position)
        notifyItemRangeChanged(position, moodItems.size)
    }

    fun updateMoodItems(moodItem: List<MoodItem>) {
        moodItems = moodItem
        notifyItemRangeChanged(0, moodItems.size)
    }
}