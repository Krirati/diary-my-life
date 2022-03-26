package com.kstudio.diarymylife.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.ItemMoodBinding

class MoodAdapter : RecyclerView.Adapter<MoodAdapter.ViewHolder>() {

    val moodList = arrayListOf(
        R.drawable.ic_pencil,
        R.drawable.ic_group_40,
        R.drawable.ic_pencil,
        R.drawable.ic_group_40,
        R.drawable.ic_pencil,
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ItemMoodBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(moodList[position])
    }

    override fun getItemCount(): Int = moodList.size

    inner class ViewHolder(private val binding: ItemMoodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Int?) = with(binding) {
            item?.let {
                imageMood.setImageResource(item)
            }
        }
    }
}