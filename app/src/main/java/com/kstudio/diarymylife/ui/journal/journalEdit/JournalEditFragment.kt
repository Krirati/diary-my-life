package com.kstudio.diarymylife.ui.journal.journalEdit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kstudio.diarymylife.databinding.FragmentJournalBinding
import com.kstudio.diarymylife.ui.base.BaseFragment

class JournalEditFragment: BaseFragment() {

    private val binding get() = _binding as FragmentJournalBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJournalBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonEdit.visibility = View.GONE
        binding.back.setOnClickListener { activity?.onBackPressed() }
        binding.buttonSave.setOnClickListener { navigateToDetail() }
    }

    private fun navigateToDetail() {
        val direction = JournalEditFragmentDirections.actionJournalEditFragmentToJournalDetailFragment()
        findNavController().navigate(direction)
    }
}