package com.kstudio.diarymylife.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.databinding.ItemActivityEventBinding
import com.kstudio.diarymylife.domain.model.ActivityEventCount
import com.kstudio.diarymylife.ui.adapter.viewholder.ItemActivityEventViewHolder

class ActivityEventAdapter : RecyclerView.Adapter<ItemActivityEventViewHolder>() {
    private var activityEventList = mutableListOf<ActivityEventCount>()

    override fun onBindViewHolder(holder: ItemActivityEventViewHolder, position: Int) {
        holder.bind(activityEventList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemActivityEventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemActivityEventViewHolder(
            ItemActivityEventBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return activityEventList.size
    }

    fun updateActivityEvent(activityEventCount: ActivityEventCount) {
        activityEventList.add(activityEventCount)
        notifyItemRangeChanged(0, activityEventList.count())
    }

    fun clearList() {
        notifyItemRangeRemoved(0, activityEventList.size)
        activityEventList.clear()
    }
}