package com.kstudio.diarymylife.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.FragmentHomeBinding
import com.kstudio.diarymylife.data.JournalItem
import com.kstudio.diarymylife.ui.adapter.ItemCardMemoryAdapter
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.ui.create.CreateJournalActivity
import com.kstudio.diarymylife.ui.journal.JournalDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel by viewModel<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        bindingView()
    }

    private fun observer() {
        homeViewModel.getMemberList().observe(viewLifecycleOwner) {
            setUpRecentMemory(it)
        }
    }

    override fun bindingView() = with(binding) {
        viewAllButton.setOnClickListener { homeViewModel.createRecentJournal() }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpRecentMemory(memberList: List<JournalItem>) {
        val memberAdapter = ItemCardMemoryAdapter(
            memberList,
            onNavigateToDetail = {
                navigateToActivity(JournalDetailActivity::class.java, it)
            },
            onDeleted = { homeViewModel.deleteJournal(it) },
            onAddItem = { navigateToCreateJournal() }
        )

        binding.recentWidget.apply {
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            isNestedScrollingEnabled = false
            adapter = memberAdapter
            onFlingListener = null
        }

        memberAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.fetchRecentJournal()
    }

    private fun navigateToCreateJournal() {
        val intent = Intent(activity, CreateJournalActivity::class.java)
        startActivity(intent)
        requireActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
    }

    override fun handleOnBackPress() {
        TODO("Not yet implemented")
    }
}