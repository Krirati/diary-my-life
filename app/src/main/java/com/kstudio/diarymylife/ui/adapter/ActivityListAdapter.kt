package com.kstudio.diarymylife.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.data.ActivityEvent
import com.kstudio.diarymylife.databinding.ItemChipActivityBinding
import com.kstudio.diarymylife.model.ActivityDetail

class ActivityListAdapter(
    private val context: Context,
    private val onClickSelect: (ActivityDetail, String) -> Unit?
) :
    RecyclerView.Adapter<ActivityListAdapter.ViewHolder>() {

    companion object {
        const val SELECT = "SELECT"
        const val UNSELECT = "UNSELECT"
    }

    private var activityItems: List<ActivityDetail> = arrayListOf()
    private var selectActivity: ArrayList<ActivityDetail> = arrayListOf()

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
        fun bind(item: ActivityDetail?) = with(binding) {
            item?.let { data ->
                val resID: Int =
                    context.resources.getIdentifier(data.activityImage, "drawable", context.packageName)
                activityImage.setImageResource(resID)
                activityName.text = data.activityName
                activityContainer.apply {
                    setOnClickListener { updateSelectActivity(data) }
                    if (data in selectActivity) {
                        binding.activityContainer.setBackgroundResource(R.drawable.bg_round_card_action_select)
                    } else {
                        binding.activityContainer.setBackgroundResource(R.drawable.bg_round_card_action)
                    }
                }
            }
        }

        private fun updateSelectActivity(data: ActivityDetail) {
            if (data in selectActivity) {
                selectActivity.remove(data)
                onClickSelect(data, UNSELECT)
            } else {
                selectActivity.add(data)
                onClickSelect(data, SELECT)
            }
            notifyItemChanged(getIndexOfData(data))
        }
    }

    fun updateActivityItems(items: List<ActivityDetail>) {
        activityItems = items
        notifyItemRangeChanged(0, activityItems.size)
    }

    fun updateFirstTimeSelectActivity(items: ArrayList<ActivityDetail>) {
        if (selectActivity.isNullOrEmpty()) {
            selectActivity = items
            notifyItemRangeChanged(0, activityItems.size)
        }

    }

    private fun getIndexOfData(data: ActivityDetail): Int = activityItems.indexOf(data)
}