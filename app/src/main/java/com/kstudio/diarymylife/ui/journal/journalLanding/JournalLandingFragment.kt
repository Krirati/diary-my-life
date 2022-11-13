package com.kstudio.diarymylife.ui.journal.journalLanding

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.data.ActivityDetail
import com.kstudio.diarymylife.data.JournalItem
import com.kstudio.diarymylife.databinding.FragmentJournalBinding
import com.kstudio.diarymylife.ui.adapter.ActivityListResultAdapter
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.ui.journal.JournalDetailViewModel
import com.kstudio.diarymylife.utils.Keys.Companion.JOURNAL_ID
import org.koin.androidx.viewmodel.ext.android.viewModel

class JournalLandingFragment :
    BaseFragment<FragmentJournalBinding>(FragmentJournalBinding::inflate) {

    private val viewModel by viewModel<JournalLandingViewModel>()
    private val journalActivityViewModel by viewModel<JournalDetailViewModel>()

    private val adapterActivity by lazy { ActivityListResultAdapter(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpArguments()
        getUpJournalDetail()
        setVisibleGone()
        bindingView()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.journalData.observe(viewLifecycleOwner) {
            if (it != null) bindingDetail(it)
        }
    }

    private fun setUpArguments() {
        journalActivityViewModel.journalId = arguments?.getLong(JOURNAL_ID)!!
    }

    override fun bindingView() = with(binding) {
        back.setOnClickListener { activity?.onBackPressed() }
        buttonEdit.setOnClickListener { navigateToEditJournal() }
    }

    private fun setVisibleGone() = with(binding) {
        buttonSave.visibility = View.GONE
        journalTitleEdit.visibility = View.GONE
        journalDescEdit.visibility = View.GONE
        iconActivityEdit.visibility = View.GONE
        iconDateEdit.visibility = View.GONE
        title.text = "Mood Detail"
    }

    private fun bindingDetail(journal: JournalItem) {
        val mood = journal.data
        binding.run {
            date.bindView(mood?.timestamp)
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
        val bundle = bundleOf(JOURNAL_ID to journalActivityViewModel.journalId)
        findNavController().navigate(
            R.id.action_journalDetailFragment_to_journalEditFragment,
            bundle
        )
    }

    override fun onResume() {
        super.onResume()
        getUpJournalDetail()
    }

    private fun getUpJournalDetail() {
        journalActivityViewModel.journalId.let { viewModel.getJournalDetailFromID(it) }
    }

    override fun handleOnBackPress() {
        TODO("Not yet implemented")
    }
}