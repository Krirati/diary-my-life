package com.kstudio.diarymylife.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.databinding.ItemMoodBinding
import com.kstudio.diarymylife.utils.Moods

class MoodAdapter : RecyclerView.Adapter<MoodAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ItemMoodBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(Moods().moodList[position])
    }

    override fun getItemCount(): Int = Moods().moodList.size

    inner class ViewHolder(private val binding: ItemMoodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Pair<Int, Int?>) = with(binding) {
            item.let {
                item.second?.let { it1 -> imageMood.setImageResource(it1) }
            }
        }
    }

    @JvmName("getMoodList1")
    fun getMoodList(): ArrayList<Pair<Int, Int>> {
        return Moods().moodList
    }
}
