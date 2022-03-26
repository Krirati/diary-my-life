package com.kstudio.diarymylife.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.databinding.ItemChipActivityBinding

class ActivityListAdapter(
    private val context: Context
) :
    RecyclerView.Adapter<ActivityListAdapter.ViewHolder>() {

    private var activityItems: ArrayList<String> = arrayListOf()

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
                val resID: Int =
                    context.resources.getIdentifier(it, "drawable", context.packageName)
                activityImage.setImageResource(resID)
            }
        }
    }

    fun updateActivityItems(items: ArrayList<String>) {
        activityItems = items
        notifyItemRangeChanged(0, activityItems.size)
    }
}