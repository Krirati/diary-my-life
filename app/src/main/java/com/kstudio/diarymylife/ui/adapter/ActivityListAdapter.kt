package com.kstudio.diarymylife.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.ItemChipActivityBinding

class ActivityListAdapter(
    private val context: Context,
    private val onClickSelect: (Pair<String, String>, String) -> Unit?
) :
    RecyclerView.Adapter<ActivityListAdapter.ViewHolder>() {

    companion object {
        const val SELECT = "SELECT"
        const val UNSELECT = "UNSELECT"
    }

    private var activityItems: ArrayList<Pair<String, String>> = arrayListOf()
    private var selectActivity: ArrayList<Pair<String, String>> = arrayListOf()

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
        fun bind(item: Pair<String, String>?) = with(binding) {
            item?.let { data ->
                val resID: Int =
                    context.resources.getIdentifier(data.second, "drawable", context.packageName)
                activityImage.setImageResource(resID)
                activityName.text = data.first
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

        private fun updateSelectActivity(data: Pair<String, String>) {
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

    fun updateActivityItems(items: ArrayList<Pair<String, String>>) {
        activityItems = items
        notifyItemRangeChanged(0, activityItems.size)
    }

    fun updateFirstTimeSelectActivity(items: ArrayList<Pair<String, String>>) {
        if (selectActivity.isNullOrEmpty()) {
            selectActivity = items
            notifyItemRangeChanged(0, activityItems.size)
        }

    }

    private fun getIndexOfData(data: Pair<String, String>): Int = activityItems.indexOf(data)
}