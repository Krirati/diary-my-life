package com.kstudio.diarymylife.ui.list

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.data.MoodItem
import com.kstudio.diarymylife.databinding.FragmentListJournalBinding
import com.kstudio.diarymylife.ui.adapter.ItemCardMemoryAdapter
import com.kstudio.diarymylife.ui.adapter.ItemCardMemoryAdapter.Companion.VIEW_ADD
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.ui.create.CreateJournalActivity
import com.kstudio.diarymylife.ui.mood.MoodDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListJournalFragment : BaseFragment<FragmentListJournalBinding>(
    FragmentListJournalBinding::inflate
) {

    private val viewModel by viewModel<ListMoodViewModel>()

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
        }
    }

    private fun observeLiveData() {
        viewModel.getMemberList().observe(viewLifecycleOwner) {
            setUpRecentMemory(it)
        }
    }


    override fun handleOnBackPress() {
        TODO("Not yet implemented")
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpRecentMemory(memberList: List<MoodItem>) {
        val memberAdapter = ItemCardMemoryAdapter(
            memberList,
            onNavigateToDetail = {
                navigateToActivity(MoodDetailActivity::class.java, it)
            },
            onDeleted = { viewModel.deleteJournal(it) },
            onAddItem = { navigateToCreateJournal() }
        )

        binding.recyclerview.apply {
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            adapter = memberAdapter
        }
        bindingCard(memberList)
        memberAdapter.notifyDataSetChanged()

    }

    private fun bindingCard(mood: List<MoodItem>) = with(binding){
        val moodTotal = mood.filter { mood -> mood.viewType != VIEW_ADD }.size
        cardTotal.setValue(moodTotal)
        cardAverage.setValue(moodTotal)
    }

    private fun navigateToCreateJournal() {
        val intent = Intent(activity, CreateJournalActivity::class.java)
        startActivity(intent)
        requireActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
    }
}