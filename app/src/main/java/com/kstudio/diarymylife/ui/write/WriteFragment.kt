package com.kstudio.diarymylife.ui.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.FragmentWriteBinding
import com.kstudio.diarymylife.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class WriteFragment : BaseFragment() {

    private val viewModel by viewModel<WriteViewModel>()

    private val binding get() = _binding as FragmentWriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleOnBackPress()
        bindingView()
    }

    private fun bindingView() = with(binding) {
        howYouFeel.text = "Hello Nine. How are you feeling?"
        back.setOnClickListener { onBackPressed() }
    }

    private fun handleOnBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackPressed()
                }
            })
    }

    private fun onBackPressed() {
        requireActivity().finishAfterTransition()
        requireActivity().overridePendingTransition(
            R.anim.slide_in_top,
            R.anim.slide_out_bottom
        )
    }
}