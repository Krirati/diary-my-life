package com.kstudio.diarymylife.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.ItemMoodBinding
import com.kstudio.diarymylife.utils.BackgroundTheme.Companion.Poor
import com.kstudio.diarymylife.utils.BackgroundTheme.Companion.Excellent
import com.kstudio.diarymylife.utils.BackgroundTheme.Companion.Average
import com.kstudio.diarymylife.utils.BackgroundTheme.Companion.Good
import com.kstudio.diarymylife.utils.BackgroundTheme.Companion.Very_Poor

class MoodAdapter : RecyclerView.Adapter<MoodAdapter.ViewHolder>() {

    private val moodList = arrayListOf(
        Pair(Excellent, R.drawable.lol),
        Pair(Good, R.drawable.smile),
        Pair(Average, R.drawable.neutral),
        Pair(Poor, R.drawable.sad),
        Pair(Very_Poor, R.drawable.crying),
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