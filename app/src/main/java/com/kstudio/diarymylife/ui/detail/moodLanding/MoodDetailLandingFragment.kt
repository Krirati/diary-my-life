package com.kstudio.diarymylife.ui.detail.moodLanding

import android.os.Bundle
import android.view.View
import com.kstudio.diarymylife.databinding.FragmentMoodListBinding
import com.kstudio.diarymylife.ui.adapter.ActivityListResultAdapter
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.ui.detail.MoodDetailViewModel
import com.kstudio.diarymylife.utils.Keys.Companion.MOOD_ID
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoodDetailLandingFragment :
    BaseFragment<FragmentMoodListBinding>(FragmentMoodListBinding::inflate) {

    private val viewModel by viewModel<MoodDetailLandingViewModel>()
    private val moodActivityViewModel by viewModel<MoodDetailViewModel>()
    private val adapterActivity by lazy { ActivityListResultAdapter(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpArguments()
        getUpMoodDetail()
        bindingView()
        observeLiveData()
    }

    private fun observeLiveData() {
    }

    private fun setUpArguments() {
        moodActivityViewModel.moodId = arguments?.getLong(MOOD_ID)!!
    }

    override fun bindingView() = with(binding) {
    }

    override fun onResume() {
        super.onResume()
        getUpMoodDetail()
    }

    private fun getUpMoodDetail() {
//        moodActivityViewModel.moodId.let { viewModel.getMoodDetailFromID(it) }
    }

    override fun handleOnBackPress() {
        TODO("Not yet implemented")
    }
}