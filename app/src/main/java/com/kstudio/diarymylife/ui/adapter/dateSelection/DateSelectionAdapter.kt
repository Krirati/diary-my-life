package com.kstudio.diarymylife.ui.adapter.dateSelection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.kstudio.diarymylife.data.DateDetailsUI
import com.kstudio.diarymylife.databinding.ItemChipDayBinding

class DateSelectionAdapter(
    val lifecycleOwner: LifecycleOwner,
    private val onDateSelection: (DateDetailsUI) -> Unit,
) :
    PagingDataAdapter<DateDetailsUI, DateSelectionViewHolder>(dateDifferentiators) {

    val selectedItems = MutableLiveData<Int>()
    var selectDate = ""

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DateSelectionViewHolder {
        val view = ItemChipDayBinding.inflate(LayoutInflater.from(parent.context))
        return DateSelectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: DateSelectionViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(this, it, onDateSelection, selectDate)
        }
    }

    fun addItemToSelection(position: Int) {
        selectedItems.value = position
    }

    fun setUpSelectDate(time: String) {
        selectDate = time
        notifyDataSetChanged()
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
