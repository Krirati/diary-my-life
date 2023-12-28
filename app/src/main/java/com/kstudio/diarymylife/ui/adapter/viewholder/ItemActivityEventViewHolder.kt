package com.kstudio.diarymylife.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.databinding.ItemActivityEventBinding
import com.kstudio.diarymylife.domain.model.ActivityEventCount

class ItemActivityEventViewHolder(
    val binding: ItemActivityEventBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(activityEventCount: ActivityEventCount) = with(binding) {
        activityImage.setImageResource(activityEventCount.activityImage)
        activityName.text = activityEventCount.activityName
        activityCount.text = activityEventCount.count.toString()
    }
}