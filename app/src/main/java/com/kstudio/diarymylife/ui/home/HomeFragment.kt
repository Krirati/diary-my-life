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
import com.kstudio.diarymylife.ui.adapter.RecentMemoryAdapter
import com.kstudio.diarymylife.ui.base.BaseFragment
import java.sql.Timestamp
import kotlin.collections.ArrayList

class HomeFragment : BaseFragment() {

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
        val card = JournalCard(
            journalId = 1,
            title = "nine",
            desc = "desc",
            timestamp = Timestamp(System.currentTimeMillis()),
            mood = "good",
            imageId = "test",
            activity = arrayListOf("1", "2", "3")
        )
        val card2 = JournalCard(
            journalId = 1,
            title = "nine2",
            desc = "desc",
            timestamp = Timestamp(3333),
            mood = "good",
            imageId = "test",
            activity = null
        )
        val card3 = JournalCard(
            journalId = 1,
            title = "nine3",
            desc = "desc",
            timestamp = Timestamp(4444),
            mood = "good",
            imageId = "test",
            activity = arrayListOf("1", "2", "3")
        )
        val memberList: ArrayList<JournalCard> = arrayListOf(card, card2, card3)
        val memberAdapter = RecentMemoryAdapter(memberList)
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
}