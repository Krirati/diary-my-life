package com.kstudio.diarymylife.ui.mood.moodLanding

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.data.ActivityDetail
import com.kstudio.diarymylife.data.MoodItem
import com.kstudio.diarymylife.databinding.FragmentJournalBinding
import com.kstudio.diarymylife.ui.adapter.ActivityListResultAdapter
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.ui.mood.MoodDetailViewModel
import com.kstudio.diarymylife.utils.Keys.Companion.MOOD_ID
import com.kstudio.diarymylife.utils.mapMoodIntToRes
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoodLandingFragment :
    BaseFragment<FragmentJournalBinding>(FragmentJournalBinding::inflate) {

    private val viewModel by viewModel<MoodLandingViewModel>()
    private val moodActivityViewModel by viewModel<MoodDetailViewModel>()
    private val adapterActivity by lazy { ActivityListResultAdapter(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpArguments()
        getUpMoodDetail()
        setVisibleGone()
        bindingView()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.moodData.observe(viewLifecycleOwner) {
            if (it != null) bindingDetail(it)
        }
    }

    private fun setUpArguments() {
        moodActivityViewModel.moodId = arguments?.getLong(MOOD_ID)!!
    }

    override fun bindingView() = with(binding) {
        back.setOnClickListener { activity?.onBackPressedDispatcher?.onBackPressed() }
        buttonEdit.setOnClickListener { navigateToEditJournal() }
    }

    private fun setVisibleGone() = with(binding) {
        buttonSave.visibility = View.GONE
        journalTitleEdit.visibility = View.GONE
        journalDescEdit.visibility = View.GONE
        iconActivityEdit.visibility = View.GONE
        iconDateEdit.visibility = View.GONE
        title.text = getString(R.string.mood_detail)
    }

    private fun bindingDetail(journal: MoodItem) {
        val mood = journal.data
        binding.run {
            date.bindView(mood?.timestamp)
            mood?.mood?.let { imageMood.setImageResource(mapMoodIntToRes(it)) }
            journalTitle.text = mood?.title
            journalDesc.text = mood?.desc
            activityTitle.visibility= View.VISIBLE
            recyclerviewActivity.apply {
                if (mood?.activity.isNullOrEmpty()) this.visibility = View.GONE
                layoutManager = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
                isNestedScrollingEnabled = false
                adapter = adapterActivity
            }.run {
                mood?.activity?.let { adapterActivity.updateActivityItems(it as ArrayList<ActivityDetail>) }
            }
        }
    }

    @SuppressLint("ResourceType")
    private fun navigateToEditJournal() {
        val bundle = bundleOf(MOOD_ID to moodActivityViewModel.moodId)
        findNavController().navigate(
            R.id.action_journalDetailFragment_to_journalEditFragment,
            bundle
        )
    }

    override fun onResume() {
        super.onResume()
        getUpMoodDetail()
    }

    private fun getUpMoodDetail() {
        moodActivityViewModel.moodId.let { viewModel.getMoodDetailFromID(it) }
    }

    override fun handleOnBackPress() {
        TODO("Not yet implemented")
    }
}