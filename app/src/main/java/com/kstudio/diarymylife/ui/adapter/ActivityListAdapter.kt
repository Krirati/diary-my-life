package com.kstudio.diarymylife.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.ItemChipActivityBinding

class ActivityListAdapter(private val activityItems: List<String?>) :
    RecyclerView.Adapter<ActivityListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ItemChipActivityBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(activityItems[position])
    }

    override fun getItemCount(): Int = activityItems.size

    inner class ViewHolder(private val binding: ItemChipActivityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String?) = with(binding) {

            item?.let {
                activityImage.setImageResource(
                    when (item) {
                        "1" -> R.drawable.ic_home
                        "2" -> R.drawable.ic_settings
                        "3" -> R.drawable.ic_plus
                        else -> R.drawable.ic_plus
                    }
                )
            }
        }
    }
}