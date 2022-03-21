package com.kstudio.diarymylife.ui.create.select_activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.kstudio.diarymylife.databinding.FragmentSelectActivityBinding
import com.kstudio.diarymylife.ui.adapter.ActivityListAdapter
import com.kstudio.diarymylife.ui.base.BaseFragment

class SelectActivityFragment : BaseFragment<FragmentSelectActivityBinding>
    (FragmentSelectActivityBinding::inflate) {

    private val adapterActivity by lazy { ActivityListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
    }

    private fun bindView() = with(binding) {
        recyclerviewActivity.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            isNestedScrollingEnabled = false
            adapter = adapterActivity
        }
    }
}