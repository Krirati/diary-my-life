package com.kstudio.diarymylife.ui.list

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.data.JournalItem
import com.kstudio.diarymylife.databinding.FragmentListJournalBinding
import com.kstudio.diarymylife.ui.adapter.ItemCardMemoryAdapter
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.ui.create.CreateJournalActivity
import com.kstudio.diarymylife.ui.journal.JournalDetailActivity
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
    private fun setUpRecentMemory(memberList: List<JournalItem>) {
        val memberAdapter = ItemCardMemoryAdapter(
            memberList,
            onNavigateToDetail = {
                navigateToActivity(JournalDetailActivity::class.java, it)
            },
            onDeleted = { viewModel.deleteJournal(it) },
            onAddItem = { navigateToCreateJournal() }
        )

        binding.recyclerview.apply {
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            adapter = memberAdapter
        }
        bindingCard(memberList.size, memberList.size)
        memberAdapter.notifyDataSetChanged()

    }

    private fun bindingCard(sizeMood:Int, diffDate: Int) = with(binding){
        cardTotal.setValue(sizeMood)
        cardAverage.setValue(diffDate)
    }

    private fun navigateToCreateJournal() {
        val intent = Intent(activity, CreateJournalActivity::class.java)
        startActivity(intent)
        requireActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
    }
}