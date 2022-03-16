package com.kstudio.diarymylife.ui.adapter.dateSelection

import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.ItemDayBinding
import com.kstudio.diarymylife.model.DateDetailsUI

class DateSelectionViewHolder(private val binding: ItemDayBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var adapter: DateSelectionAdapter

    fun bind(
        adapter: DateSelectionAdapter,
        dateDetailsUI: DateDetailsUI,
        onDateSelection: (DateDetailsUI) -> Unit
    ) {
        this.adapter = adapter

        binding.apply {
            day.text = dateDetailsUI.dayOfWeek.substring(0, 3)
            dayNumber.text = dateDetailsUI.day.toString()
            containerDate.setOnClickListener { view ->
                onDateSelection.invoke(dateDetailsUI)
                layoutPosition.let { adapter.addItemToSelection(it) }
                view.setBackgroundResource(R.drawable.bg_round_card_action_select)
            }
        }

        adapter.selectedItems.observe(adapter.lifecycleOwner) {
            layoutPosition.let { ps ->
                if (ps != it) {
                    binding.containerDate.setBackgroundResource(R.drawable.bg_round_card_action)
                }
            }
        }
    }
}