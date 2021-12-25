package com.kstudio.diarymylife.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.kstudio.diarymylife.databinding.FragmentHomeBinding
import com.kstudio.diarymylife.model.JournalCard
import com.kstudio.diarymylife.adapter.RecentMemoryAdapter
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.ui.journal.JournalDetailActivity
import kotlin.collections.ArrayList

class HomeFragment : BaseFragment() {

    private lateinit var homeViewModel: HomeViewModel

    private val binding get() = _binding as FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        homeViewModel.fetchMemberList()
    }

    private fun observer() {
        homeViewModel.getMemberList().observe(viewLifecycleOwner) {
            setUpRecentMemory(it)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpRecentMemory(memberList: ArrayList<JournalCard>) {
        val memberAdapter = RecentMemoryAdapter(
            memberList,
            callback = {navigateToActivity(JournalDetailActivity::class.java, it)}
        ) {  }

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
        homeViewModel.fetchMemberList()
    }
}