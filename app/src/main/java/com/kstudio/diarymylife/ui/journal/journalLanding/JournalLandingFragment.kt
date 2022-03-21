package com.kstudio.diarymylife.ui.journal.journalLanding

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.kstudio.diarymylife.data.Journal
import com.kstudio.diarymylife.databinding.FragmentJournalBinding
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.ui.journal.JournalDetailViewModel
import com.kstudio.diarymylife.utils.Keys.Companion.JOURNAL_ID
import org.koin.androidx.viewmodel.ext.android.viewModel

class JournalLandingFragment :
    BaseFragment<FragmentJournalBinding>(FragmentJournalBinding::inflate) {
    private val viewModel by viewModel<JournalLandingViewModel>()
    private val journalActivityViewModel by viewModel<JournalDetailViewModel>()



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
            bindingDetail(it)
        }
    }

    private fun setUpArguments() {
        if (journalActivityViewModel.journalId == null) {
            journalActivityViewModel.journalId = arguments?.getLong(JOURNAL_ID)
        }
    }

    private fun bindingView() = with(binding) {
        back.setOnClickListener { activity?.onBackPressed() }
        buttonEdit.setOnClickListener {
            navigateToEditJournal()
        }
    }

    private fun setVisibleGone() = with(binding) {
        buttonSave.visibility = View.GONE
        journalTitleEdit.visibility = View.GONE
        journalDescEdit.visibility = View.GONE
        iconActivityEdit.visibility = View.GONE
        iconDateEdit.visibility = View.GONE
        title.text = "Journal Detail"
    }

    private fun bindingDetail(journal: Journal) {
        binding.run {
            date.bindView(journal.timestamp)
            journalTitle.text = journal.title
            journalDesc.text = journal.description
        }
    }

    @SuppressLint("ResourceType")
    private fun navigateToEditJournal() {
        val direction =
            journalActivityViewModel.journalId?.let {
                JournalLandingFragmentDirections.actionJournalDetailFragmentToJournalEditFragment(
                    it
                )
            }
        if (direction != null) {
            findNavController().navigate(direction)
        }
    }

    override fun onResume() {
        super.onResume()
        getUpJournalDetail()
    }

    private fun getUpJournalDetail() {
        journalActivityViewModel.journalId?.let { viewModel.getJournalDetailFromID(it) }
    }
}