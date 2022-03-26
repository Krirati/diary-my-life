package com.kstudio.diarymylife.ui.create.result_journal

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.kstudio.diarymylife.databinding.FragmentJournalBinding
import com.kstudio.diarymylife.ui.base.BaseFragment

class ResultJournalFragment :
    BaseFragment<FragmentJournalBinding>(FragmentJournalBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingView()
        handleOnBackPress()
    }

    override fun bindingView() = with(binding){
        setVisibleGone()
        back.setOnClickListener { onBackPressed() }
    }

    override fun onBackPressed() {
        findNavController().navigateUp()
    }

    override fun handleOnBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackPressed()
                }
            })
    }

    private fun setVisibleGone() = with(binding) {
        buttonEdit.visibility = View.GONE
        journalTitle.visibility = View.GONE
        journalDesc.visibility = View.GONE
        iconDateEdit.visibility = View.GONE
        iconActivityEdit.visibility = View.GONE
    }
}