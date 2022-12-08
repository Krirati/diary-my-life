package com.kstudio.diarymylife.ui.create.select_mood

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.data.ResultSelectDate
import com.kstudio.diarymylife.databinding.FragmentSelectMoodBinding
import com.kstudio.diarymylife.ui.adapter.MoodAdapter
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.utils.Formats.Companion.DATE_TIME_FORMAT_APP
import com.kstudio.diarymylife.utils.convertTime
import com.kstudio.diarymylife.utils.dpToPx
import com.kstudio.diarymylife.widgets.select_date_bottomsheet.SelectDateBottomSheet
import com.kstudio.diarymylife.widgets.select_date_bottomsheet.SelectDateHandle
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime

class SelectMoodFragment :
    BaseFragment<FragmentSelectMoodBinding>(FragmentSelectMoodBinding::inflate), SelectDateHandle {

    private val viewModel by viewModel<SelectMoodViewModel>()
    private val adapterMood by lazy { MoodAdapter() }
    private val bottomSheetSelectTime by lazy {
        SelectDateBottomSheet.newInstance(
            requireContext(),
            ::onClickDoneBottomSheet,
            ::onCloseBottomSheet,
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleOnBackPress()
        setupViewPager()
        bindingView()
        observe()
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
            setPadding(offsetPx, 0, offsetPx, 0)
            setPageTransformer(marginTransformer)
            binding.dotsIndicator.setViewPager2(this)
        }
    }

    override fun bindingView() = with(binding) {
        howYouFeel.text = getString(R.string.how_are_you_feel)
        currentSelectTime.text = convertTime(LocalDateTime.now(), DATE_TIME_FORMAT_APP)
        currentSelectTime.setOnClickListener {
            bottomSheetSelectTime.show(childFragmentManager, bottomSheetSelectTime.tag)
        }
        buttonNext.setOnClickListener {
            viewModel.createMood()
        }
        back.setOnClickListener { onBackPressedOrFinish() }
    }

    private fun observe() {
        viewModel.created.observe(viewLifecycleOwner) {
            // todo model success icon and
            activity?.finishAfterTransition()
        }
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

    override fun onClickDoneBottomSheet(date: ResultSelectDate) {
        date.day?.let {
            viewModel.setSelectDate(it)
        }
        date.time?.let {
            viewModel.setSelectTime(it)
        }
        binding.currentSelectTime.text = convertTime(date.getLocalDateTime())
    }

    override fun onCloseBottomSheet() {
//        val currentDate = shareViewModel.selectedDate.value
//        if (currentDate != null) {
//            shareViewModel.updateCurrentSelectedDate(currentDate, true)
//        }
    }

    @SuppressLint("ResourceType")
    private fun navigateToNextScreen() {
        findNavController().navigate(R.id.action_writeFragment_to_selectActivityFragment)
    }

    private fun onPageChangeCallback() =
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.setupSelectMood(adapterMood.getMoodList()[position])
            }
        }
}