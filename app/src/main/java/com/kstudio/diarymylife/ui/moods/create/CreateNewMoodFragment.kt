package com.kstudio.diarymylife.ui.moods.create

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.chip.ChipGroup
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.data.ResultSelectDate
import com.kstudio.diarymylife.databinding.FragmentMoodBinding
import com.kstudio.diarymylife.domain.model.Event
import com.kstudio.diarymylife.ui.adapter.MoodAdapter
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.utils.Formats.Companion.DATE_TIME_FORMAT_APP
import com.kstudio.diarymylife.utils.Permissions
import com.kstudio.diarymylife.utils.convertTime
import com.kstudio.diarymylife.utils.dpToPx
import com.kstudio.diarymylife.widgets.ChipView
import com.kstudio.diarymylife.widgets.event_bottomsheet.EventBottomSheet
import com.kstudio.diarymylife.widgets.select_date_bottomsheet.SelectDateBottomSheet
import com.kstudio.diarymylife.widgets.select_date_bottomsheet.SelectDateHandle
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime

class CreateNewMoodFragment :
    BaseFragment<FragmentMoodBinding>(FragmentMoodBinding::inflate), SelectDateHandle {

    private val viewModel by viewModel<CreateNewMoodViewModel>()
    private val adapterMood by lazy { MoodAdapter() }

    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                handleSelectImage(uri)
            }
        }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                selectImageFromGalleryResult.launch("image/*")
            } else {
                Toast.makeText(
                    activity,
                    "Please allow permission for access media",
                    Toast.LENGTH_LONG
                ).show()
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
            clipToPadding = false // allow full width shown with padding
            clipChildren = false // allow left/right item is not clipped
            offscreenPageLimit = 2
            setPadding(offsetPx, 0, offsetPx, 0)
            setPageTransformer(marginTransformer)
            binding.dotsIndicator.setViewPager2(this)
        }
    }

    override fun bindingView() = with(binding) {
        currentSelectTime.text = convertTime(LocalDateTime.now(), DATE_TIME_FORMAT_APP)
        currentSelectTime.setOnClickListener {
            val bottomSheetSelectTime = SelectDateBottomSheet(
                requireContext(),
                ::onClickDoneBottomSheet,
                ::onCloseBottomSheet,
            )
            bottomSheetSelectTime.show(childFragmentManager, bottomSheetSelectTime.tag)
        }
        buttonNext.apply {
            isEnabled = false
            setOnClickListener { viewModel.createNewMood() }
        }
        back.setOnClickListener {
            onBackPressedOrFinish(
                transitionIn = R.anim.slide_in_top,
                transitionOut = R.anim.slide_out_bottom
            )
        }
        selectedImage.setOnClickListener { selectImageFromGallery() }
        buttonImage.setOnClickListener { selectImageFromGallery() }
        imageView.setOnClickListener { selectImageFromGallery() }
        addActivityButton.setOnClickListener { openSelectEventBottomSheet() }
    }

    private fun observe() {
        viewModel.nickname.observe(viewLifecycleOwner) {
            binding.howYouFeel.text = getString(R.string.how_are_you_feel).replace("{Name}", it)
        }
        viewModel.created.observe(viewLifecycleOwner) {
            activity?.finishAfterTransition()
        }
    }

    override fun handleOnBackPress() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackPressedOrFinish()
                }
            }
        )
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

    private fun openSelectEventBottomSheet() {
        val bottomSheetSelectEvent = EventBottomSheet(
            getContext = requireContext(),
            onClose = ::onSelectEventBottomSheetClose,
        )
        activity?.supportFragmentManager?.let { fragmentManager ->
            bottomSheetSelectEvent.show(
                fragmentManager,
                CreateNewMoodFragment::class.java.simpleName
            )
        }
    }

    private fun onSelectEventBottomSheetClose(eventList: List<Event>?) {
        Log.d("test", ">> onSelectEventBottomSheetClose $eventList")
    }

    private fun selectImageFromGallery() {
        Permissions.requirePermissionNotification(
            requireContext(),
            Manifest.permission.READ_MEDIA_IMAGES,
            callBack = {
                selectImageFromGalleryResult.launch("image/*")
            },
            requireAccept = { requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES) })
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

    private fun ChipGroup.addChildChip(newChip: ChipView) {
        addView(newChip)
    }

    private fun createChip(activityName: String, icon: Int, bgColor: Int): ChipView {
        return ChipView(requireContext()).apply {
            text = activityName
            setImageChipIcon(icon)
            setChipBackgroundColor(bgColor)
            setOnClickCloseIcon { binding.chipGroup.removeView(this) }
        }
    }
}
