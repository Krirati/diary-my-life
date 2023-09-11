package com.kstudio.diarymylife.ui.detail.moodLanding

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.data.MoodUI
import com.kstudio.diarymylife.databinding.FragmentMoodCreateBinding
import com.kstudio.diarymylife.ui.adapter.MoodAdapter
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.utils.FileUtility.getUriImage
import com.kstudio.diarymylife.utils.Keys.Companion.MOOD_ID
import com.kstudio.diarymylife.utils.dpToPx
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoodDetailLandingFragment :
    BaseFragment<FragmentMoodCreateBinding>(FragmentMoodCreateBinding::inflate) {

    private val viewModel by viewModel<MoodDetailLandingViewModel>()
    private val adapterMood by lazy { MoodAdapter() }

    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                handleSelectImage(uri)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        bindingView()
        bindTextField()
        handleOnBackPress()
        observeLiveData()
        setUpArguments()
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

    private fun onPageChangeCallback() =
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.setupSelectMood(adapterMood.getMoodList()[position])
            }
        }

    private fun observeLiveData() {
        viewModel.moodData.observe(viewLifecycleOwner) {
            it?.data?.let { mood ->
                bindMoodData(mood)
                viewModel.setImageUri(getUriImage(requireContext(), mood.fileName))
                viewModel.setUpInitDetail(mood)
            }
        }
    }

    private fun bindMoodData(mood: MoodUI) {
        binding.apply {
            val imageURI = getUriImage(requireContext(), mood.fileName)
            moodDesc.setDefaultTextValue(mood.desc)
            imageView.apply {
                visibility = View.VISIBLE
                setImageURI(imageURI)
            }
            viewPagerMood.currentItem = adapterMood.mapMoodToPosition(mood.mood)
        }
    }

    private fun setUpArguments() {
        val moodID = arguments?.getLong(MOOD_ID)
        if (moodID == null) this.activity?.finish()
        else viewModel.getMoodDetailFromID(moodID)
    }

    override fun bindingView() = with(binding) {
        title.text = "Detail Mood"
        buttonNext.setOnClickListener { viewModel.updateMoodDetail() }
        selectedImage.setOnClickListener { selectImageFromGallery() }
        buttonImage.setOnClickListener { selectImageFromGallery() }
        imageView.setOnClickListener { selectImageFromGallery() }
    }

    private fun bindTextField() = with(binding) {
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
}