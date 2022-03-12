package com.kstudio.diarymylife.ui.write

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.FragmentWriteBinding
import com.kstudio.diarymylife.model.ResultSelectDate
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.ui.widgets.SelectDateBottomSheet
import com.kstudio.diarymylife.utils.convertTime
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
        observeLiveData()
        bindingView()
    }

    private fun bindingView() = with(binding) {
        howYouFeel.text = "Hello Nine. How are you feeling?"
        currentSelectTime.text = convertTime(LocalDateTime.now(), "MMMM, dd EEEE hh:mm")
        currentSelectTime.setOnClickListener {
            val bottomSheetSelectTime = SelectDateBottomSheet(
                requireContext(),
                ::onClickDoneBottomSheet
            )
            bottomSheetSelectTime.show(childFragmentManager, "bottom sheet date")
        }
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

    private fun onClickDoneBottomSheet(date: ResultSelectDate) {
        viewModel.setSelectDate(
            LocalDate.parse(
                date.day.toString(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
            )
        )
        binding.currentSelectTime.text = date.day.toString()
    }

    private fun observeLiveData() {
        viewModel.localDateTimeSelect.observe(viewLifecycleOwner) {
            Log.d("test", "ob ::" + it)
        }
    }
}