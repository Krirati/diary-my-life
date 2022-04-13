package com.kstudio.diarymylife.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.databinding.ItemChipActivityResultBinding

class ActivityListResultAdapter(private val context: Context) :
    RecyclerView.Adapter<ActivityListResultAdapter.ViewHolder>() {

    private var activityItems: ArrayList<Pair<String, String>> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ItemChipActivityResultBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(activityItems[position])
    }

    override fun getItemCount(): Int = activityItems.size

    inner class ViewHolder(private val binding: ItemChipActivityResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Pair<String, String>?) = with(binding) {
            item?.let { data ->
                val resID: Int =
                    context.resources.getIdentifier(data.second, "drawable", context.packageName)
                activityImage.setImageResource(resID)
                activityName.text = data.first
            }
        }
    }

    fun updateActivityItems(items: ArrayList<Pair<String, String>>) {
        activityItems = items
        notifyItemRangeChanged(0, activityItems.size)
    }
}