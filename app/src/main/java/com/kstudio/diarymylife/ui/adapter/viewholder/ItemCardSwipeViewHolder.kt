package com.kstudio.diarymylife.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.databinding.ItemSwipeCardBinding
import com.kstudio.diarymylife.domain.model.MoodViewType

class ItemCardSwipeViewHolder(
    val binding: ItemSwipeCardBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: MoodViewType,
        onActionClickDelete: (Int) -> Unit,
        onClickToDetail: (Long) -> Unit
    ) {
        binding.apply {
            item.data?.apply {
                customCard.setTitleAndDate(this.mood.toString(), this.timestamp)
                customCard.setOnClickWidget { onClickToDetail(this.moodId) }
                customCard.setOnClickAction { onActionClickDelete(absoluteAdapterPosition) }
            }
        }
    }
}