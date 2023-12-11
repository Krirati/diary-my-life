package com.kstudio.diarymylife.widgets.event_bottomsheet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.ItemEventBinding
import com.kstudio.diarymylife.domain.model.Event

class EventAdapter(
    private val onUpdateSelectedList: (List<Event>) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var eventList: MutableList<Event> = mutableListOf()
    private var eventSelectedList: MutableList<Event> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EventViewHolder(ItemEventBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as EventViewHolder).bind(
            event = eventList[position],
            ::addToSelectedList,
            ::removeFromSelectedList
        )
    }

    fun updateEventList(newList: List<Event>) {
        eventList = newList.toMutableList()
        notifyItemChanged(0, newList.size)
    }

    private fun addToSelectedList(event: Event, position: Int) {
        eventSelectedList.add(event)
        eventList[position] = event
        onUpdateSelectedList(eventSelectedList)
        notifyItemChanged(position)
    }

    private fun removeFromSelectedList(event: Event, position: Int) {
        eventSelectedList.removeIf { it.eventId == event.eventId }
        eventList[position] = event
        onUpdateSelectedList(eventSelectedList)
        notifyItemChanged(position)
    }

    class EventViewHolder(
        private val binding: ItemEventBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            event: Event,
            addToSelectedList: (event: Event, position: Int) -> Unit,
            removeFromSelectedList: (event: Event, position: Int) -> Unit
        ) = with(binding) {
            chipImage.setImageResource(R.drawable.crying)
            textView.text = event.activityName
            checkButton.isVisible = event.isSelected
            root.setOnClickListener {
                if (event.isSelected) {
                    removeFromSelectedList(event.copy(isSelected = false), layoutPosition)
                    checkButton.isVisible = false
                } else {
                    addToSelectedList(event.copy(isSelected = true), layoutPosition)
                    checkButton.isVisible = true
                }
            }
        }
    }
}