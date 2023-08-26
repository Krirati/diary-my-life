package com.kstudio.diarymylife.ui.create.select_mood

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.data.ResultSelectDate
import com.kstudio.diarymylife.databinding.FragmentMoodCreateBinding
import com.kstudio.diarymylife.ui.adapter.MoodAdapter
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.utils.Formats.Companion.DATE_TIME_FORMAT_APP
import com.kstudio.diarymylife.utils.convertTime
import com.kstudio.diarymylife.utils.dpToPx
import com.kstudio.diarymylife.widgets.select_date_bottomsheet.SelectDateBottomSheet
import com.kstudio.diarymylife.widgets.select_date_bottomsheet.SelectDateHandle
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime

class MoodCreateFragment :
    BaseFragment<FragmentMoodCreateBinding>(FragmentMoodCreateBinding::inflate), SelectDateHandle {

    private val viewModel by viewModel<SelectMoodViewModel>()
    private val adapterMood by lazy { MoodAdapter() }

    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                binding.buttonImage.visibility = View.GONE
                binding.imageView.apply {
                    setImageURI(uri)
                    visibility = View.VISIBLE
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleOnBackPress()
        setupViewPager()
        bindingView()
        observe()
        bindTextField()
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
            val bottomSheetSelectTime = SelectDateBottomSheet(
                requireContext(),
                ::onClickDoneBottomSheet,
                ::onCloseBottomSheet,
            )
            bottomSheetSelectTime.show(childFragmentManager, bottomSheetSelectTime.tag)
        }
        buttonNext.setOnClickListener {
            viewModel.createMood()
        }
        back.setOnClickListener { onBackPressedOrFinish() }
        selectedImage.setOnClickListener { selectImageFromGallery() }
        buttonImage.setOnClickListener { selectImageFromGallery() }
        imageView.setOnClickListener { selectImageFromGallery() }
    }

    private fun observe() {
        viewModel.created.observe(viewLifecycleOwner) {
            // todo model success icon and
            activity?.finishAfterTransition()
        }

        viewModel.isChange.observe(viewLifecycleOwner) {
            binding.buttonNext.isEnabled = it
        }
    }

    override fun handleOnBackPress() {
        activity?.onBackPressedDispatcher?.addCallback(
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

    override fun onCloseBottomSheet() { /* DO nothing */
    }

    private fun onPageChangeCallback() =
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.setupSelectMood(adapterMood.getMoodList()[position])
            }
        }

    private fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")

    private fun bindTextField() = with(binding) {
        moodDesc.setOnTextChange { s, _, _, _ ->
            viewModel.setMoodDesc(s.toString())
        }
    }
}