package com.kstudio.diarymylife.ui.create.result_journal

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kstudio.diarymylife.databinding.FragmentJournalBinding
import com.kstudio.diarymylife.ui.adapter.ActivityListResultAdapter
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.ui.create.CreateJournalViewModel

class ResultJournalFragment :
    BaseFragment<FragmentJournalBinding>(FragmentJournalBinding::inflate) {

    private val shearViewModel: CreateJournalViewModel by activityViewModels()
    private val adapterActivity by lazy {
        ActivityListResultAdapter(
            requireContext(),
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingView()
        handleOnBackPress()
        observeLiveDate()
        observeEditText()
    }

    override fun bindingView() = with(binding) {
        recyclerviewActivity.apply {
            layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
            isNestedScrollingEnabled = false
            adapter = adapterActivity
        }
        setVisibleGone()
        back.setOnClickListener { onBackPressedOrFinish() }
        buttonSave.setOnClickListener { addNewJournal() }
    }

    override fun onBackPressedOrFinish() {
        findNavController().navigateUp()
    }

    override fun handleOnBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackPressedOrFinish()
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

    private fun observeLiveDate() {
        shearViewModel.selectsMood.observe(viewLifecycleOwner) {
            setUpImageMood(it)
        }
        shearViewModel.localDateSelect.observe(viewLifecycleOwner) {
            binding.date.bindView(it)
        }
        shearViewModel.localTimeSelect.observe(viewLifecycleOwner) {
            binding.date.bindTime(it)
        }
        shearViewModel.selectsActivity.observe(viewLifecycleOwner) {
            if (it != null && it.size > 0) {
                adapterActivity.updateActivityItems(it)
                showActivity()
            } else {
                hideActivity()
            }
        }
    }

    private fun observeEditText() {
        binding.journalTitleEdit.doOnTextChanged { text, start, before, count ->
            shearViewModel.setUpTitleJournal(text.toString())
        }
        binding.journalDescEdit.doOnTextChanged { text, start, before, count ->
            shearViewModel.setUpDescJournal(text.toString())
        }
    }

    private fun setUpImageMood(data: Pair<Int, Int>) {
        binding.imageMood.setBackgroundResource(data.second)
    }

    private fun hideActivity() = with(binding) {
        activityTitle.visibility = View.GONE
        recyclerviewActivity.visibility = View.GONE
    }

    private fun showActivity() = with(binding) {
        activityTitle.visibility = View.VISIBLE
        recyclerviewActivity.visibility = View.VISIBLE
    }

    private fun addNewJournal() {
        shearViewModel.createRecentJournal()
        onBackPressedOrFinish()
    }
}