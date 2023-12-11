package com.kstudio.diarymylife.widgets.event_bottomsheet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.ItemEventBinding
import com.kstudio.diarymylife.domain.model.Event

class EventAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var eventList: List<Event> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EventViewHolder(ItemEventBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as EventViewHolder).bind(event = eventList[position])
    }

    fun updateEventList(newList: List<Event>) {
        eventList = newList
        notifyItemChanged(0, newList.size)
    }
}

internal class EventViewHolder(
    private val binding: ItemEventBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(event: Event) {
        binding.chipImage.setImageResource(R.drawable.crying)
        binding.textView.text = event.title
    }
}