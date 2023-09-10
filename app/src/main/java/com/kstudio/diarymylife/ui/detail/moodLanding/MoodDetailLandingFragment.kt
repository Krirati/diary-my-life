package com.kstudio.diarymylife.ui.detail.moodLanding

import android.os.Bundle
import android.view.View
import com.kstudio.diarymylife.databinding.FragmentMoodCreateBinding
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.utils.FileUtility.getUriImage
import com.kstudio.diarymylife.utils.Keys.Companion.MOOD_ID
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoodDetailLandingFragment :
    BaseFragment<FragmentMoodCreateBinding>(FragmentMoodCreateBinding::inflate) {

    private val moodActivityViewModel by viewModel<MoodDetailLandingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingView()
        handleOnBackPress()
        observeLiveData()
        setUpArguments()
    }

    private fun observeLiveData() {
        moodActivityViewModel.moodData.observe(viewLifecycleOwner) {
            it?.data?.let { mood ->
                binding.apply {
                    val imageURI = getUriImage(requireContext(), mood.fileName)
                    moodDesc.setDefaultTextValue(mood.desc)
                    buttonImage.visibility = View.GONE
                    imageView.apply {
                        visibility = View.VISIBLE
                        setImageURI(imageURI)
                    }
                }
            }
        }
    }

    private fun setUpArguments() {
        val moodID = arguments?.getLong(MOOD_ID)
        if (moodID == null) this.activity?.finish()
        else moodActivityViewModel.getMoodDetailFromID(moodID)
    }

    override fun bindingView() = with(binding) {
        title.text = "Detail Mood"
    }

    override fun handleOnBackPress() {
        binding.back.setOnClickListener { this.activity?.finish() }
    }
}