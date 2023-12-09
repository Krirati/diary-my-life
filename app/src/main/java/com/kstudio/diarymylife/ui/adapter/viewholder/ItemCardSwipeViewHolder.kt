package com.kstudio.diarymylife.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.databinding.ItemSwipeCardBinding
import com.kstudio.diarymylife.domain.model.MoodViewType
import com.kstudio.diarymylife.utils.Moods

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
                val colorTheme = Moods().mapperThemeColor(this.mood)
                customCard.setTitleAndDate(
                    title = item.data?.title ?: "",
                    detail = item.data?.desc ?: "",
                    date = this.timestamp
                )
                customCard.setBackgroundAndKeepPadding(colorTheme.second)
                customCard.setPillTag(
                    Moods().mapMoodStringToTitle(this.mood),
                    colorTheme.first
                )
                customCard.setOnClickWidget { onClickToDetail(this.moodId) }
                customCard.setOnClickAction { onActionClickDelete(absoluteAdapterPosition) }
            }
        }
    }
}
