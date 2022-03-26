package com.kstudio.diarymylife.ui.create.select_mood

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.FragmentSelectMoodBinding
import com.kstudio.diarymylife.model.ResultSelectDate
import com.kstudio.diarymylife.ui.adapter.MoodAdapter
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.ui.create.CreateJournalViewModel
import com.kstudio.diarymylife.ui.widgets.select_date_bottomsheet.SelectDateBottomSheet
import com.kstudio.diarymylife.ui.widgets.select_date_bottomsheet.SelectDateHandle
import com.kstudio.diarymylife.utils.Formats.Companion.DATE_TIME_FORMAT_APP
import com.kstudio.diarymylife.utils.convertTime
import com.kstudio.diarymylife.utils.dpToPx
import com.kstudio.diarymylife.utils.toStringFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime

class SelectMoodFragment :
    BaseFragment<FragmentSelectMoodBinding>(FragmentSelectMoodBinding::inflate), SelectDateHandle {

    private val viewModel by viewModel<SelectMoodViewModel>()
    private val shareViewModel: CreateJournalViewModel by activityViewModels()

    private val adapterMood by lazy { MoodAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleOnBackPress()
        setupViewPager()
        observeLiveData()
        bindingView()
    }

    private fun setupViewPager() {
        val offsetPx =
            resources.getDimension(R.dimen.dp_16).toInt().dpToPx(resources.displayMetrics)
        val pageMarginPx =
            resources.getDimension(R.dimen.dp_6).toInt().dpToPx(resources.displayMetrics)
        val marginTransformer = MarginPageTransformer(pageMarginPx)

        binding.viewPagerMood.apply {
            registerOnPageChangeCallback(onPageChangeCallback())
            adapter = adapterMood
            clipToPadding = false   // allow full width shown with padding
            clipChildren = false    // allow left/right item is not clipped
            offscreenPageLimit = 2
            currentItem = 2
            setPadding(offsetPx, 0, offsetPx, 0)
            setPageTransformer(marginTransformer)
            binding.dotsIndicator.setViewPager2(this)
        }
    }

    override fun bindingView() = with(binding) {
        howYouFeel.text = "Hello Nine. How are you feeling?"
        currentSelectTime.text = convertTime(LocalDateTime.now(), DATE_TIME_FORMAT_APP)
        currentSelectTime.setOnClickListener {
            val bottomSheetSelectTime = SelectDateBottomSheet(
                requireContext(),
                ::onClickDoneBottomSheet,
                ::onCloseBottomSheet,
                viewModel
            )
            bottomSheetSelectTime.show(childFragmentManager, "bottom sheet date")
        }
        buttonNext.setOnClickListener { navigateToNextScreen() }
        back.setOnClickListener { onBackPressed() }
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

    override fun onClickDoneBottomSheet(date: ResultSelectDate) {
        date.day?.let {
            viewModel.setSelectDate(it)
            viewModel.updateCurrentSelectedDate(it.toStringFormat(), true)
        }
        date.time?.let {
            viewModel.setSelectTime(it)
        }
        binding.currentSelectTime.text = convertTime(date.getLocalDateTime())
    }

    override fun onCloseBottomSheet() {
        val currentDate = viewModel.selectedDate.value
        if (currentDate != null) {
            viewModel.updateCurrentSelectedDate(currentDate, true)
        }
    }

    private fun observeLiveData() {
        viewModel.localDateSelect.observe(viewLifecycleOwner) {
            Log.d("test", "ob ::" + it)
        }

        viewModel.selectedDate.observe(viewLifecycleOwner) {
            Log.d("test", "ob frag ::" + it)
        }
    }

    @SuppressLint("ResourceType")
    private fun navigateToNextScreen() {
        val direction =
            SelectMoodFragmentDirections.actionWriteFragmentToSelectActivityFragment()
        findNavController().navigate(direction)
    }

    private fun onPageChangeCallback() =
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                shareViewModel.setupSelectMood(position)
            }
        }
}