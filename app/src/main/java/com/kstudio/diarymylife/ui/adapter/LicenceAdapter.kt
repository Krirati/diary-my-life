package com.kstudio.diarymylife.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kstudio.diarymylife.databinding.ItemLicenceBinding
import com.kstudio.diarymylife.ui.setting.license.LicenceDetail

class LicenceAdapter(private val onClickView: (String) -> Unit) :
    RecyclerView.Adapter<LicenceAdapter.ItemLicenceViewHolder>() {
    private var licenceList = mutableListOf<LicenceDetail>()

    override fun onBindViewHolder(holder: ItemLicenceViewHolder, position: Int) {
        holder.bind(licenceList[position], onClickView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemLicenceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemLicenceViewHolder(
            ItemLicenceBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return licenceList.size
    }

    fun updateLicenceList(list: List<LicenceDetail>) {
        licenceList = list.toMutableList()
        notifyItemRangeChanged(0, licenceList.count())
    }


    inner class ItemLicenceViewHolder(
        val binding: ItemLicenceBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(licence: LicenceDetail, onClickView: (String) -> Unit) = with(binding) {
            activityName.text = licence.title
            root.setOnClickListener { onClickView.invoke(licence.url) }
        }
    }
}

