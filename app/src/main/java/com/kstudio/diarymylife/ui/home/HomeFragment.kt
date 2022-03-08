package com.kstudio.diarymylife.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.FragmentHomeBinding
import com.kstudio.diarymylife.model.JournalItem
import com.kstudio.diarymylife.ui.adapter.ItemCardMemoryAdapter
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.ui.journal.JournalDetailActivity
import com.kstudio.diarymylife.ui.write.WriteActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {

    private val homeViewModel by viewModel<HomeViewModel>()

    private val binding get() = _binding as FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        binding()
        homeViewModel.fetchRecentJournal()
    }

    private fun observer() {
        homeViewModel.getMemberList().observe(viewLifecycleOwner) {
            setUpRecentMemory(it)
        }
    }

    private fun binding() = with(binding) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.fetchRecentJournal()
    }

    private fun navigateToCreateJournal() {
        val intent = Intent(activity, WriteActivity::class.java)
        startActivity(intent)
        requireActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
    }
}