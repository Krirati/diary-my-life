package com.kstudio.diarymylife.ui.summary

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.kstudio.diarymylife.databinding.FragmentSummaryMoodBinding
import com.kstudio.diarymylife.domain.model.MoodViewType
import com.kstudio.diarymylife.ui.adapter.ItemCardSwipeAdapter.Companion.VIEW_ADD
import com.kstudio.diarymylife.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SummaryMoodFragment : BaseFragment<FragmentSummaryMoodBinding>(
    FragmentSummaryMoodBinding::inflate
) {

    private val viewModel by viewModel<SummaryMoodViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingView()
        observeLiveData()
        viewModel.fetchRecentJournal()
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
    }

    override fun bindingView() {
    }

    override fun handleOnBackPress() {
    }

    private fun bindingCard(mood: List<MoodViewType>) = with(binding) {
        val moodTotal = mood.filter { mood -> mood.viewType != VIEW_ADD }
    }
}
