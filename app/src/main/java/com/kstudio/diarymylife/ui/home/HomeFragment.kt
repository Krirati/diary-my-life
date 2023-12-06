package com.kstudio.diarymylife.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.FragmentHomeBinding
import com.kstudio.diarymylife.domain.model.MoodViewType
import com.kstudio.diarymylife.ui.adapter.ItemCardSwipeAdapter
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.ui.create.CreateMoodActivity
import com.kstudio.diarymylife.ui.detail.MoodDetailActivity
import com.kstudio.diarymylife.ui.list.ListActivity
import com.kstudio.diarymylife.ui.setting.SettingActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel by viewModel<HomeViewModel>()

    private val moodAdapter by lazy {
        ItemCardSwipeAdapter(
            onAddItem = { navigateToCreateJournal() },
            onDeleted = { homeViewModel.deleteJournal(it) },
            onNavigateToDetail = { navigateToActivity(MoodDetailActivity::class.java, it) },
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        bindingView()
        homeViewModel.performWelcomeText()
        handleOnBackPress()
    }

    private fun observer() = with(homeViewModel) {
        getMemberList().observe(viewLifecycleOwner) {
            updateRecentMood(it)
        }
        welcomeText.observe(viewLifecycleOwner) {
            binding.titleWelcome.text = it
        }
    }

    override fun bindingView() = with(binding) {
        titleUsername.text = homeViewModel.getUserName()
        recentWidget.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            isNestedScrollingEnabled = false
            adapter = moodAdapter
            onFlingListener = null
        }
        checkInButton.setOnClickListener {
            navigateToActivity(
                CreateMoodActivity::class.java,
                transitionIn = R.anim.slide_in_bottom,
                transitionOut = R.anim.slide_out_top
            )
        }
        settingButton.setOnClickListener { navigateToActivity(SettingActivity::class.java) }
        seeAll.setOnClickListener { navigateToActivity(ListActivity::class.java) }
    }

    private fun updateRecentMood(moodList: List<MoodViewType>) {
        binding.recentWidget.apply {
            removeAllViews()
            recycledViewPool.clear()
        }
        moodAdapter.updateMoodItems(moodList)
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.fetchRecentJournal()
    }

    private fun navigateToCreateJournal() {
        val intent = Intent(activity, CreateMoodActivity::class.java)
        startActivity(intent)
        requireActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
    }

    override fun handleOnBackPress() {
        activity?.onBackPressedDispatcher?.addCallback {
            minimizeApp()
        }
    }

    private fun minimizeApp() {
        val startMain = Intent(Intent.ACTION_MAIN)
        startMain.addCategory(Intent.CATEGORY_HOME)
        startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(startMain)
    }

}