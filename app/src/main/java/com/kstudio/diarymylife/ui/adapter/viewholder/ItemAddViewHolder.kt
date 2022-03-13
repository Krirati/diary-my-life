package com.kstudio.diarymylife.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.databinding.ItemCardAddBinding

class ItemAddViewHolder(
    val binding: ItemCardAddBinding,
    private val onAddItem: () -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind() = with(binding) {
        journalAdd.setOnClickListener{onAddItem()}
    }
}