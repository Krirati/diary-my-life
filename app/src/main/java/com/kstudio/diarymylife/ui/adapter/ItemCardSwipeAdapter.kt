package com.kstudio.diarymylife.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.databinding.ItemCardAddBinding
import com.kstudio.diarymylife.databinding.ItemSwipeCardBinding
import com.kstudio.diarymylife.domain.model.MoodViewType
import com.kstudio.diarymylife.ui.adapter.viewholder.ItemAddViewHolder
import com.kstudio.diarymylife.ui.adapter.viewholder.ItemCardSwipeViewHolder

class ItemCardSwipeAdapter(
    private val onAddItem: () -> Unit,
    private val onDeleted: (Long?) -> Unit?,
    private val onNavigateToDetail: (Long?) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var moodItems: List<MoodViewType> = listOf()

    companion object {
        const val VIEW_ITEM = 10
        const val VIEW_ADD = 11
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_ITEM -> ItemCardSwipeViewHolder(
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
            VIEW_ITEM -> {
                (holder as ItemCardSwipeViewHolder).bind(
                    item = moodItems[position],
                    onActionClickDelete = ::onClickDeleteItem,
                    onClickToDetail = onNavigateToDetail
                )
            }
            VIEW_ADD -> (holder as ItemAddViewHolder).bind()
        }
    }

    private fun onClickDeleteItem(position: Int) {
        onDeleted(moodItems[position].data?.moodId)
        moodItems.drop(position)
        notifyItemRangeChanged(position, moodItems.size)
    }

    fun updateMoodItems(moodItem: List<MoodViewType>) {
        moodItems = moodItem
        notifyDataSetChanged()
    }
}