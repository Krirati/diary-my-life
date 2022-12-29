package com.kstudio.diarymylife.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.data.MoodItem
import com.kstudio.diarymylife.databinding.ItemSwipeCardBinding

class ItemCardSwipeViewHolder(
    val binding: ItemSwipeCardBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: MoodItem,
        onActionClickDelete: (Int) -> Unit,
        onClickToDetail: (Long) -> Unit
    ) {
        binding.apply {
            item.data?.apply {
                customCard.setTitleAndDate(this.title, this.timestamp)
                customCard.setOnClickWidget { onClickToDetail(this.moodId) }
                customCard.setOnClickAction { onActionClickDelete(absoluteAdapterPosition) }
            }
        }
    }
}