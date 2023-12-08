package com.kstudio.diarymylife.ui.adapter.dateSelection

import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.data.DateDetailsUI
import com.kstudio.diarymylife.databinding.ItemChipDayBinding

class DateSelectionViewHolder(private val binding: ItemChipDayBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var adapter: DateSelectionAdapter

    fun bind(
        adapter: DateSelectionAdapter,
        dateDetailsUI: DateDetailsUI,
        onDateSelection: (DateDetailsUI) -> Unit,
        selectDate: String
    ) {
        this.adapter = adapter

        if (dateDetailsUI.dateKey == selectDate) {
            binding.apply {
                containerDate.setBackgroundResource(R.drawable.bg_round_card_action_select)
                day.isSelected = true
                dayNumber.isSelected = true
            }
        } else {
            binding.apply {
                containerDate.setBackgroundResource(R.drawable.bg_round_card_action)
                day.isSelected = false
                dayNumber.isSelected = false
            }
        }

        binding.apply {
            day.text = dateDetailsUI.dayOfWeek.substring(0, 3)
            dayNumber.text = dateDetailsUI.day.toString()
            containerDate.setOnClickListener { view ->
                onDateSelection.invoke(dateDetailsUI)
                layoutPosition.let { adapter.addItemToSelection(it) }
                view.setBackgroundResource(R.drawable.bg_round_card_action_select)
            }
        }
    }
}
