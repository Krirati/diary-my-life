package com.kstudio.diarymylife.ui.journal.journalEdit

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kstudio.diarymylife.data.ActivityDetail
import com.kstudio.diarymylife.data.JournalItem
import com.kstudio.diarymylife.data.ResultSelectDate
import com.kstudio.diarymylife.databinding.FragmentJournalBinding
import com.kstudio.diarymylife.ui.adapter.ActivityListResultAdapter
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.utils.Keys
import com.kstudio.diarymylife.utils.toStringFormat
import com.kstudio.diarymylife.widgets.select_date_bottomsheet.SelectDateBottomSheet
import com.kstudio.diarymylife.widgets.select_date_bottomsheet.SelectDateHandle
import org.koin.androidx.viewmodel.ext.android.viewModel

class JournalEditFragment : BaseFragment<FragmentJournalBinding>(FragmentJournalBinding::inflate),
    SelectDateHandle {
    private val viewModel by viewModel<JournalEditViewModel>()
    private val adapterActivity by lazy { ActivityListResultAdapter(requireContext()) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        observeEditText()
        initJournal()
        setVisibleGone()
        bindingView()
    }

    private fun initJournal() {
        val id = arguments?.getLong(Keys.JOURNAL_ID)
        viewModel.getJournalDetailFromID(id)
    }


    override fun bindingView() {
        binding.apply {
            back.setOnClickListener { handleOnBackPress() }
            dateTitle.setOnClickListener { displayBottomSheetSelectDate() }
            iconDateEdit.setOnClickListener { displayBottomSheetSelectDate() }
        }
    }

    private fun displayBottomSheetSelectDate() {
        val bottomSheetSelectTime = SelectDateBottomSheet(
            requireContext(),
            ::onClickDoneBottomSheet,
            ::onCloseBottomSheet
        )
        bottomSheetSelectTime.show(childFragmentManager, "bottom sheet date")
    }

    private fun observeLiveData() {
        viewModel.journalDetail.observe(viewLifecycleOwner) {
            if (it != null) setUpView(it)
        }

        viewModel.event.observe(viewLifecycleOwner) {
            navigateToDetail()
        }

        viewModel.isChanged.observe(viewLifecycleOwner) {
            binding.buttonSave.isEnabled = viewModel.isEditJournal()
        }
    }

    private fun setUpView(mood: JournalItem) = with(binding) {
        title.text = "Mood Edit"
        date.bindView(mood.data?.timestamp)
        journalTitleEdit.setText(mood.data?.title)
        journalDescEdit.setText(mood.data?.desc)
        buttonSave.setOnClickListener { viewModel.updateJournal() }
        activityTitle.visibility = View.VISIBLE
        recyclerviewActivity.apply {
            if (mood.data?.activity.isNullOrEmpty()) this.visibility = View.GONE
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
            isNestedScrollingEnabled = false
            adapter = adapterActivity
        }.run {
            mood.data?.activity?.let { adapterActivity.updateActivityItems(it as ArrayList<ActivityDetail>) }
        }
    }

    private fun setVisibleGone() = with(binding) {
        buttonEdit.visibility = View.GONE
        journalTitle.visibility = View.GONE
        journalDesc.visibility = View.GONE
    }

    private fun navigateToDetail() {
        findNavController().navigateUp()
    }

    override fun handleOnBackPress() {
        findNavController().navigateUp()
    }

    override fun onClickDoneBottomSheet(date: ResultSelectDate) {
        date.day?.let {
            viewModel.setSelectDate(it)
            viewModel.updateCurrentSelectedDate(it.toStringFormat(), true)
        }
        date.time?.let {
            viewModel.setSelectTime(it)
        }
        binding.date.bindView(date.getLocalDateTime())
        viewModel.setTimestamp(date.getLocalDateTime())
    }

    override fun onCloseBottomSheet() {
        val currentDate = viewModel.selectedDate.value
        if (currentDate != null) {
            viewModel.updateCurrentSelectedDate(currentDate, true)
        }
    }

    private fun observeEditText() {
        binding.apply {
            journalTitleEdit.doOnTextChanged { text, _, _, count ->
                viewModel.setTitle(text.toString())
                if (count == 0) binding.buttonSave.isEnabled = false
            }
            journalDescEdit.doOnTextChanged { text, _, _, _ ->
                viewModel.setDescription(text.toString())
            }
        }
    }

}