package com.kstudio.diarymylife.ui.journal.journalEdit

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kstudio.diarymylife.databinding.FragmentJournalBinding
import com.kstudio.diarymylife.entity.Mood
import com.kstudio.diarymylife.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class JournalEditFragment : BaseFragment<FragmentJournalBinding>(FragmentJournalBinding::inflate) {
    private val viewModel by viewModel<JournalEditViewModel>()
    private val args: JournalEditFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        viewModel.getJournalDetailFromID(args.journalId)
        setVisibleGone()
        bindingView()
    }


    override fun bindingView() {
        binding.back.setOnClickListener { activity?.onBackPressed() }
    }

    private fun observeLiveData() {
        viewModel.journalData.observe(viewLifecycleOwner) {
            setUpView(it)
        }
    }

    private fun setUpView(mood: Mood) = with(binding) {
        title.text = "Mood Edit"
        date.bindView(mood.timestamp)
        journalTitleEdit.setText(mood.title)
        journalDescEdit.setText(mood.description)
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
            val journal = Mood(
                moodId = args.journalId,
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

    override fun handleOnBackPress() {
        TODO("Not yet implemented")
    }

}