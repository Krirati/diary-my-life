package com.kstudio.diarymylife.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.ItemMoodBinding

class MoodAdapter : RecyclerView.Adapter<MoodAdapter.ViewHolder>() {

    private val moodList = arrayListOf(
        Pair(0, R.drawable.mood1),
        Pair(1, R.drawable.mood2),
        Pair(2, R.drawable.mood3),
        Pair(3, R.drawable.mood4),
        Pair(4, R.drawable.mood5)
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
        fun bind(item: Pair<Int,Int?>) = with(binding) {
            item.let {
                item.second?.let { it1 -> imageMood.setImageResource(it1) }
            }
        }
    }

    @JvmName("getMoodList1")
    fun getMoodList(): ArrayList<Pair<Int, Int>> {
        return moodList
    }
}