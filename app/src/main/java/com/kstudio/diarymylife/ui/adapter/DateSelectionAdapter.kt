package com.kstudio.diarymylife.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.ItemDayBinding
import com.kstudio.diarymylife.model.DateDetailsUI

class DateSelectionAdapter(
    val lifecycleOwner: LifecycleOwner,
    private val onDateSelection: (DateDetailsUI) -> Unit,
) :
    PagingDataAdapter<DateDetailsUI, DateSelectionAdapter.DateViewHolder>(dateDifferentiators) {

    private val selectedItems = MutableLiveData<Int>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DateSelectionAdapter.DateViewHolder {
        val view = ItemDayBinding.inflate(LayoutInflater.from(parent.context))
        return DateViewHolder(view)
    }

    override fun onBindViewHolder(holder: DateSelectionAdapter.DateViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(this, it)
        }
    }

    /**
     * Add an item it the selection.
     */
    fun addItemToSelection(position: Int) {
        selectedItems.value = position
    }

    inner class DateViewHolder(private val binding: ItemDayBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var adapter: DateSelectionAdapter

        fun bind(adapter: DateSelectionAdapter, dateDetailsUI: DateDetailsUI) {
            this.adapter = adapter
            layoutPosition.let {
                if (it == 0) {
                    binding.containerDate.setBackgroundResource(R.drawable.bg_round_card_action_select)
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

            adapter.selectedItems.observe(adapter.lifecycleOwner) {
                layoutPosition.let { ps ->
                    if (ps != it) {
                        binding.containerDate.setBackgroundResource(R.drawable.bg_round_card_action)
                    }
                }
            }
        }
    }
}

val dateDifferentiators = object : DiffUtil.ItemCallback<DateDetailsUI>() {

    override fun areItemsTheSame(oldItem: DateDetailsUI, newItem: DateDetailsUI): Boolean {
        return oldItem.dateKey == newItem.dateKey
    }

    override fun areContentsTheSame(oldItem: DateDetailsUI, newItem: DateDetailsUI): Boolean {
        return oldItem == newItem
    }
}