package com.kstudio.diarymylife.ui.summary

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kstudio.diarymylife.databinding.FragmentSummaryMoodBinding
import com.kstudio.diarymylife.ui.adapter.ActivityEventAdapter
import com.kstudio.diarymylife.ui.base.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SummaryMoodFragment : BaseFragment<FragmentSummaryMoodBinding>(
    FragmentSummaryMoodBinding::inflate
) {

    private val viewModel by viewModel<SummaryMoodViewModel>()
    private val adapterActivityEvent by lazy { ActivityEventAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingView()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.averageMood.observe(viewLifecycleOwner) {
        }
        viewModel.barData.observe(viewLifecycleOwner) {
            binding.chartSection.isVisible = it.second.isNotEmpty()
            binding.chart.apply {
                setMaxValue(it.first)
                setDataList(it.second)
                build()
            }
        }

        lifecycleScope.launch {
            viewModel.uiState.collect {
                if (it == null) {
                    binding.activitySectionEmpty.isVisible = true
                    adapterActivityEvent.clearList()
                } else {
                    binding.activitySectionEmpty.isVisible = false
                    adapterActivityEvent.updateActivityEvent(it)
                }
            }
        }
    }

    override fun bindingView() = with(binding) {
        activityEventRecyclerView.apply {
            adapter = adapterActivityEvent
            addItemDecoration(
                DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
            )
        }
        return@with
    }

    override fun handleOnBackPress() { /* Do nothing*/
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchRecentJournal()
        viewModel.fetchActivityEventGroup()
    }
}
