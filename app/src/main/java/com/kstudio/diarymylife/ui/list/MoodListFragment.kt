package com.kstudio.diarymylife.ui.list

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.FragmentMoodListBinding
import com.kstudio.diarymylife.domain.model.MoodViewType
import com.kstudio.diarymylife.ui.adapter.ItemCardSwipeAdapter
import com.kstudio.diarymylife.ui.adapter.ItemCardSwipeAdapter.Companion.VIEW_ADD
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.ui.create.CreateJournalActivity
import com.kstudio.diarymylife.ui.detail.MoodDetailActivity
import com.kstudio.diarymylife.widgets.custom_chart.BarData
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoodListFragment : BaseFragment<FragmentMoodListBinding>(
    FragmentMoodListBinding::inflate
) {

    private val viewModel by viewModel<ListMoodViewModel>()
    private val moodAdapter by lazy {
        ItemCardSwipeAdapter(
            onAddItem = { navigateToCreateJournal() },
            onDeleted = { viewModel.deleteJournal(it) },
            onNavigateToDetail = { navigateToActivity(MoodDetailActivity::class.java, it) },
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingView()
        observeLiveData()
        viewModel.fetchRecentJournal()
        bindingChart()
    }

    override fun bindingView() {
        binding.apply {
            cardAverage.bind("Mood avg", 5, R.drawable.pie_chart_24)
            cardTotal.bind("Total mood", 5, R.drawable.ic_bar_chart_24)
            recyclerview.apply {
                layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
                adapter = moodAdapter
            }
        }

    }

    private fun observeLiveData() {
        viewModel.getMemberList().observe(viewLifecycleOwner) {
            setUpRecentMemory(it)
        }

        viewModel.averageMood.observe(viewLifecycleOwner) {
            binding.cardAverage.setValue(it)
        }
    }

    private fun bindingChart() {
        val dataList = ArrayList<BarData>()

        var data = BarData(3.4f, R.color.sandy_brown, R.drawable.mood1)
        dataList.add(data)

        data = BarData(8f, R.color.deep_champagne, R.drawable.mood2)
        dataList.add(data)

        data = BarData(1.8f, R.color.lemon_yellow_crayola, R.drawable.mood3)
        dataList.add(data)

        data = BarData(10f, R.color.pale_sprint_bud, R.drawable.mood4)
        dataList.add(data)

        data = BarData(6.2f, R.color.laurel_green, R.drawable.mood5)
        dataList.add(data)

        binding.chart.apply {
            setDataList(dataList)
            build()
        }
    }

    override fun handleOnBackPress() {
        TODO("Not yet implemented")
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpRecentMemory(moodList: List<MoodViewType>) {
        bindingCard(moodList)
        moodAdapter.updateMoodItems(moodList)
    }

    private fun bindingCard(mood: List<MoodViewType>) = with(binding) {
        val moodTotal = mood.filter { mood -> mood.viewType != VIEW_ADD }
        cardTotal.setValue(moodTotal.size.toLong())
    }

    private fun navigateToCreateJournal() {
        val intent = Intent(activity, CreateJournalActivity::class.java)
        startActivity(intent)
        requireActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
    }
}