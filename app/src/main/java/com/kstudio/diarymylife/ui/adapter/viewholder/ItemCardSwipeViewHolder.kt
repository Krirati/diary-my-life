package com.kstudio.diarymylife.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.databinding.ItemSwipeCardBinding
import com.kstudio.diarymylife.domain.model.MoodViewType
import com.kstudio.diarymylife.utils.BackgroundTheme

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
                val colorTheme = BackgroundTheme().mapperThemeColor(this.mood)
                customCard.setTitleAndDate(
                    BackgroundTheme().mapMoodStringToTitle(this.mood),
                    this.timestamp
                )
                customCard.setBackgroundAndKeepPadding(colorTheme.second)
                customCard.setPillTag(
                    BackgroundTheme().mapMoodStringToTitle(this.mood),
                    colorTheme.first
                )
                customCard.setOnClickWidget { onClickToDetail(this.moodId) }
                customCard.setOnClickAction { onActionClickDelete(absoluteAdapterPosition) }
            }
        }
    }
}