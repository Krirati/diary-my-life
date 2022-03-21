package com.kstudio.diarymylife.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.ItemChipDayBinding
import com.kstudio.diarymylife.utils.convertTime
import java.time.LocalDateTime

class DateTimeAdapter(
//    private val dateList: ArrayList<LocalDateTime>,
    private val onSelectDate: (LocalDateTime?) -> Unit?,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dateList: ArrayList<LocalDateTime> = arrayListOf()
    private var itemSelect = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemChipDayBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(
            item = dateList[position],
            onSelect = ::onSelect,
            position = position
        )
        if (itemSelect == position) {
            holder.setBackGroundColor(true)
        } else {
            holder.setBackGroundColor(false)
        }
    }

    override fun getItemCount(): Int = dateList.size

    inner class ViewHolder(
        private val binding: ItemChipDayBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LocalDateTime?, onSelect: (Int) -> Unit, position: Int) = with(binding) {
            item?.let {
                day.text = convertTime(item, "EE")
                dayNumber.text = convertTime(item, "dd")
                containerDate.setOnClickListener {
                    onSelectDate(item)
                    onSelect(position)
                }
            }
        }

        fun setBackGroundColor(b: Boolean) = with(binding) {
            if (b) {
                containerDate.setBackgroundResource(R.drawable.bg_round_card_action_select)
            } else {
                containerDate.setBackgroundResource(R.drawable.bg_round_card_action)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(list: LocalDateTime) {
        dateList.add(list)
        dateList.sort()
        notifyDataSetChanged()
    }

    private fun onSelect(position: Int) {
        notifyItemChanged(itemSelect)
        itemSelect = position
        notifyItemChanged(itemSelect)
    }
}