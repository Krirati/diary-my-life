package com.kstudio.diarymylife.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.kstudio.diarymylife.databinding.FragmentHomeBinding
import com.kstudio.diarymylife.ui.base.BaseFragment

class HomeFragment: BaseFragment() {

    private lateinit var homeViewModel: HomeViewModel

    private val binding get() = _binding as FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(layoutInflater)
        val root: View = binding.root

        setUpRecentMemory()
        return root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpRecentMemory() {
        val memberList :ArrayList<String> = arrayListOf("1", "2", "3")

        val memberAdapter = RecentMemoryAdapter(memberList)

        binding.recentWidget.apply {
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
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
}