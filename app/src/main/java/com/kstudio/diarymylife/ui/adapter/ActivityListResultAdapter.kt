package com.kstudio.diarymylife.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.databinding.ItemChipActivityResultBinding
import com.kstudio.diarymylife.data.ActivityDetail

class ActivityListResultAdapter(private val context: Context) :
    RecyclerView.Adapter<ActivityListResultAdapter.ViewHolder>() {

    private var activityItems: ArrayList<ActivityDetail> = arrayListOf()

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
        fun bind(item: ActivityDetail?) = with(binding) {
            item?.let { data ->
                val resID: Int =
                    context.resources.getIdentifier(data.activityImage, "drawable", context.packageName)
                activityImage.setImageResource(resID)
                activityName.text = data.activityName
            }
        }
    }

    fun updateActivityItems(items: ArrayList<ActivityDetail>) {
        activityItems = items
        notifyItemRangeChanged(0, activityItems.size)
    }
}