package com.kstudio.diarymylife.ui.moods.detail

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.chip.ChipGroup
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.data.MoodUI
import com.kstudio.diarymylife.data.ResultSelectDate
import com.kstudio.diarymylife.databinding.FragmentMoodBinding
import com.kstudio.diarymylife.domain.model.Event
import com.kstudio.diarymylife.ui.adapter.MoodAdapter
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.utils.FileUtility.getUriImage
import com.kstudio.diarymylife.utils.Formats
import com.kstudio.diarymylife.utils.Keys.Companion.MOOD_ID
import com.kstudio.diarymylife.utils.Moods
import com.kstudio.diarymylife.utils.convertTime
import com.kstudio.diarymylife.utils.dpToPx
import com.kstudio.diarymylife.widgets.ChipView
import com.kstudio.diarymylife.widgets.select_date_bottomsheet.SelectDateBottomSheet
import com.kstudio.diarymylife.widgets.select_date_bottomsheet.SelectDateHandle
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailMoodFragment :
    BaseFragment<FragmentMoodBinding>(FragmentMoodBinding::inflate), SelectDateHandle {

    private val viewModel by viewModel<DetailMoodViewModel>()
    private val adapterMood by lazy { MoodAdapter() }

    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                handleSelectImage(uri)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpArguments()
        setupViewPager()
        bindingView()
        bindTextField()
        handleOnBackPress()
        observeLiveData()
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
            clipToPadding = false // allow full width shown with padding
            clipChildren = false // allow left/right item is not clipped
            offscreenPageLimit = 2
            setPadding(offsetPx, 0, offsetPx, 0)
            setPageTransformer(marginTransformer)
            binding.dotsIndicator.setViewPager2(this)
        }
    }

    private fun onPageChangeCallback() =
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.setupSelectMood(adapterMood.getMoodList()[position])
            }
        }

    private fun observeLiveData() {
        viewModel.nickname.observe(viewLifecycleOwner) {
            binding.howYouFeel.text = getString(R.string.how_are_you_feel).replace("{Name}", it)
        }

        viewModel.oldMoodData.observe(viewLifecycleOwner) {
            it?.let { mood ->
                bindMoodData(mood)
                createChipActivity(mood.activityEvent)
                viewModel.setUpInitDetail(mood)
            }
        }

        viewModel.update.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Update successful!", Toast.LENGTH_LONG).show()
        }
    }

    private fun bindMoodData(mood: MoodUI) {
        binding.apply {
            val imageURI = mood.fileName?.let { getUriImage(requireContext(), it) }
            viewModel.setOldImageUri(imageURI)

            moodTitle.setDefaultTextValue(mood.title)
            moodDesc.setDefaultTextValue(mood.description)
            imageView.apply {
                visibility = View.VISIBLE
                setImageURI(imageURI)
                viewModel.setImageUri(imageURI)
            }
            viewPagerMood.currentItem = Moods().mapMoodToPosition(mood.mood)
            currentSelectTime.text = convertTime(mood.timestamp, Formats.DATE_TIME_FORMAT_APP)
        }
    }

    private fun setUpArguments() {
        val moodID = arguments?.getLong(MOOD_ID)
        if (moodID == null) this.activity?.finish()
        else viewModel.getMoodDetailFromID(moodID)
    }

    override fun bindingView() = with(binding) {
        title.text = getString(R.string.detail_mood)
        howYouFeel.text = getString(R.string.how_are_you_feel)
        currentSelectTime.setOnClickListener {
            val bottomSheetSelectTime = SelectDateBottomSheet(
                getContext = requireContext(),
                onClickDone = ::onClickDoneBottomSheet,
                onClose = ::onCloseBottomSheet,
                currentTimeSelected = viewModel.getLocalDateTime()
            )
            bottomSheetSelectTime.show(childFragmentManager, bottomSheetSelectTime.tag)
        }
        buttonNext.setOnClickListener { viewModel.updateMoodDetail() }
        selectedImage.setOnClickListener { selectImageFromGallery() }
        buttonImage.setOnClickListener { selectImageFromGallery() }
        imageView.setOnClickListener { selectImageFromGallery() }
    }

    private fun bindTextField() = with(binding) {
        moodTitle.setOnTextChange { s, _, _, _ ->
            viewModel.setMoodTitle(s.toString())
            binding.buttonNext.isEnabled = s.toString().isNotEmpty()
        }

        moodDesc.setOnTextChange { s, _, _, _ ->
            viewModel.setMoodDesc(s.toString())
        }
    }

    override fun handleOnBackPress() {
        binding.back.setOnClickListener { this.activity?.finish() }
    }

    private fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")

    private fun handleSelectImage(uri: Uri?) {
        binding.apply {
            buttonImage.visibility = View.GONE
            imageView.apply {
                setImageURI(uri)
                visibility = View.VISIBLE
            }
        }
        viewModel.setImageUri(uri)
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

    private fun createChipActivity(activityEvent: List<Event>?) {
        binding.chipGroup.removeAllViews()
        activityEvent?.forEach { event ->
            binding.chipGroup.addChildChip(createChip(event))
        }
    }

    private fun ChipGroup.addChildChip(newChip: ChipView) {
        addView(newChip)
    }

    private fun createChip(event: Event): ChipView {
        return ChipView(requireContext()).apply {
            text = event.activityName
            setImageChipIcon(event.icon)
            setChipBackgroundColor(event.backgroundColor)
            setOnClickCloseIcon {
                viewModel.removeEventSelectedList(event)
                binding.chipGroup.removeView(this)
            }
        }
    }
}
