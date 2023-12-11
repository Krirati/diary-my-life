package com.kstudio.diarymylife.widgets.event_bottomsheet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.databinding.ItemAddEventBinding

class AddEventAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AddEventViewHolder(ItemAddEventBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AddEventViewHolder).bind()
    }
}

internal class AddEventViewHolder(
    private val binding: ItemAddEventBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind() {
        binding.root.setOnClickListener {  }
    }
}