package com.kstudio.diarymylife.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.databinding.ItemRecentEventBinding
import com.kstudio.diarymylife.model.JournalCard
import com.kstudio.diarymylife.utils.DateView
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RecentMemoryAdapter(private val memoryItems: ArrayList<JournalCard>, private val callback: () -> Unit) :
    RecyclerView.Adapter<RecentMemoryAdapter.ViewHolder>() {

    var previousTime: Date = Timestamp(0)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ItemRecentEventBinding.inflate(inflater, parent, false),
            context = parent.context
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(memoryItems[position])
    }

    override fun getItemCount(): Int = memoryItems.size

    inner class ViewHolder(
        private val binding: ItemRecentEventBinding,
        private val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(item: JournalCard) = with(binding) {
            val activityAdapter = item.activity?.let { ActivityListAdapter(it) }
            val fmt = SimpleDateFormat("yyyyMMdd")

            if (!fmt.format(previousTime).equals(fmt.format(item.timestamp))) {
                val dateView = DateView(context)
                dateView.bindView(item.timestamp)
                binding.timeContainer.addView(dateView)
                previousTime = item.timestamp
            }

            journalTitle.text = item.title
            journalDesc.text = item.desc
            journalTime.text = convertTime(item.timestamp)
            journalActivity.apply {
                layoutManager = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
                isNestedScrollingEnabled = false
                adapter = activityAdapter
                onFlingListener = null
            }
            journalCard.setOnClickListener {
                callback()
            }
        }

        @SuppressLint("SimpleDateFormat")
        private fun convertTime(timestamp: Date): String {
            val sdf = SimpleDateFormat("hh:mm")
            return sdf.format(timestamp)
        }
    }
}