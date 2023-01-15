package com.kstudio.diarymylife.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.databinding.ItemSwipeCardBinding
import com.kstudio.diarymylife.domain.model.MoodViewType
import com.kstudio.diarymylife.utils.BackgroundTheme
import com.kstudio.diarymylife.utils.mapMoodStringToTitle

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
                customCard.setTitleAndDate(mapMoodStringToTitle(this.mood), this.timestamp)
                customCard.setBackgroundAndKeepPadding(BackgroundTheme().mapperThemeCard(this.mood ?: 3))
                customCard.setOnClickWidget { onClickToDetail(this.moodId) }
                customCard.setOnClickAction { onActionClickDelete(absoluteAdapterPosition) }
            }
        }
    }
}