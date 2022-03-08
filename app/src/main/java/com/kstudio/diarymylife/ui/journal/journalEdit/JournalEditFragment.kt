package com.kstudio.diarymylife.ui.journal.journalEdit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kstudio.diarymylife.data.Journal
import com.kstudio.diarymylife.databinding.FragmentJournalBinding
import com.kstudio.diarymylife.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class JournalEditFragment : BaseFragment() {
    private val binding get() = _binding as FragmentJournalBinding
    private val viewModel by viewModel<JournalEditViewModel>()
    private val args: JournalEditFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJournalBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        viewModel.getJournalDetailFromID(args.journalId)
        setVisibleGone()

        binding.back.setOnClickListener { activity?.onBackPressed() }
    }

    private fun observeLiveData() {
        viewModel.journalData.observe(viewLifecycleOwner) {
            setUpView(it)
        }
    }

    private fun setUpView(journal: Journal) = with(binding) {
        title.text = "Journal Edit"
        date.bindView(journal.timestamp)
        journalTitleEdit.setText(journal.title)
        journalDescEdit.setText(journal.description)
        buttonSave.setOnClickListener {
            updateJournalDetail()
        }
    }

    private fun setVisibleGone() = with(binding) {
        buttonEdit.visibility = View.GONE
        journalTitle.visibility = View.GONE
        journalDesc.visibility = View.GONE
    }

    private fun updateJournalDetail() {
        binding.let {
            val journal = Journal(
                id = args.journalId,
                title = it.journalTitleEdit.text.toString(),
                description = it.journalDescEdit.text.toString(),
                mood = viewModel.journalData.value!!.mood,
                activity = viewModel.journalData.value!!.activity,
                imageName = viewModel.journalData.value!!.imageName,
                timestamp = it.date.getLocalDateTime(),
                createTime = viewModel.journalData.value!!.createTime,
            )
            viewModel.updateJournal(journal)
        }
        navigateToDetail()
    }

    private fun navigateToDetail() {
        findNavController().navigateUp()
    }
}