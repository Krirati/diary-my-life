package com.kstudio.diarymylife.ui.list

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.data.MoodItem
import com.kstudio.diarymylife.databinding.FragmentListJournalBinding
import com.kstudio.diarymylife.ui.adapter.ItemCardSwipeAdapter
import com.kstudio.diarymylife.ui.adapter.ItemCardSwipeAdapter.Companion.VIEW_ADD
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.ui.create.CreateJournalActivity
import com.kstudio.diarymylife.ui.detail.MoodDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListJournalFragment : BaseFragment<FragmentListJournalBinding>(
    FragmentListJournalBinding::inflate
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
    }

    override fun bindingView() {
        binding.apply {
            cardAverage.bind("Mood average", 5, R.drawable.ic_pie_chart)
            cardTotal.bind("Total mood", 5, R.drawable.ic_calendar)
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


    override fun handleOnBackPress() {
        TODO("Not yet implemented")
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpRecentMemory(moodList: List<MoodItem>) {
        bindingCard(moodList)
        moodAdapter.updateMoodItems(moodList)
    }

    private fun bindingCard(mood: List<MoodItem>) = with(binding) {
        val moodTotal = mood.filter { mood -> mood.viewType != VIEW_ADD }
        cardTotal.setValue(moodTotal.size.toLong())
    }

    private fun navigateToCreateJournal() {
        val intent = Intent(activity, CreateJournalActivity::class.java)
        startActivity(intent)
        requireActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
    }
}