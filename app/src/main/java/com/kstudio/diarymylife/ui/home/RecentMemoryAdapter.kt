package com.kstudio.diarymylife.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.databinding.ItemRecentEventBinding

class RecentMemoryAdapter(private val memoryItems: ArrayList<String>) :
    RecyclerView.Adapter<RecentMemoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ItemRecentEventBinding.inflate(inflater,parent, false)
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(memoryItems[position])
    }

    override fun getItemCount(): Int = memoryItems.size

    inner class ViewHolder(private val binding: ItemRecentEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) = with(binding) {
            accountName.text = item
        }
    }
}