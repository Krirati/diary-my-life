package com.kstudio.diarymylife.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.databinding.ItemCardEventBinding
import com.kstudio.diarymylife.model.JournalCard
import com.kstudio.diarymylife.ui.base.SwipeEvent.SwipeState
import com.kstudio.diarymylife.viewholder.ItemCardMemoryViewHolder
import java.util.*
import kotlin.collections.ArrayList

class ItemCardMemoryAdapter(
    private val memoryItems: ArrayList<JournalCard>,
    private val callback: (String) -> Unit,
    private val onDeleted: (String) -> Unit?,
) :
    RecyclerView.Adapter<ItemCardMemoryViewHolder>() {

    private val swipeState : SwipeState = SwipeState.LEFT_RIGHT
    private var previousTime: Date? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemCardMemoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemCardMemoryViewHolder(
            ItemCardEventBinding.inflate(inflater, parent, false),
            context = parent.context,
            callback,
        )
    }

    override fun onBindViewHolder(holder: ItemCardMemoryViewHolder, position: Int) {
        holder.bind(memoryItems[position], swipeState, previousTime) { index ->
            deleteItem(index)
        }
        previousTime = memoryItems[position].timestamp
    }

    override fun getItemCount(): Int = memoryItems.size

    private fun deleteItem(position : Int) {
        memoryItems.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, memoryItems.size)
        onDeleted(memoryItems[position].journalId)
    }

}