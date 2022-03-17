package com.kstudio.diarymylife.ui.widgets.select_date_bottomsheet

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearSnapHelper
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.BottomSheetSelectDateBinding
import com.kstudio.diarymylife.model.DateDetailsUI
import com.kstudio.diarymylife.model.ResultSelectDate
import com.kstudio.diarymylife.model.toDateDetails
import com.kstudio.diarymylife.ui.adapter.dateSelection.DateSelectionAdapter
import com.kstudio.diarymylife.ui.base.BaseViewModel
import com.kstudio.diarymylife.utils.toDate
import com.kstudio.diarymylife.utils.toLocalDate
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class SelectDateBottomSheet @Inject constructor(
    private val getContext: Context,
    private val onClickDone: (ResultSelectDate) -> Unit,
    private val onClose: () -> Unit,
    private val viewModel: BaseViewModel
) : BottomSheetDialogFragment() {

    private val parentView =
        LinearLayout.inflate(getContext, R.layout.bottom_sheet_select_date, null)

    private val binding by lazy { BottomSheetSelectDateBinding.bind(parentView) }
    private var resultSelectDate: ResultSelectDate =
        ResultSelectDate(LocalDate.now(), LocalTime.now())

    private val dateAdapter by lazy { DateSelectionAdapter(this, ::setSelectedDate) }

    private var date: String = ""
    private var time: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = object :
            BottomSheetDialog(getContext, theme) {
            override fun onBackPressed() {
                this@SelectDateBottomSheet.dismissAllowingStateLoss()
            }
        }
        dialog.setCanceledOnTouchOutside(false)
        dialog.behavior.state = STATE_HIDDEN
        dialog.behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    STATE_COLLAPSED,
                    STATE_HIDDEN -> {
                        onClose()
                        this@SelectDateBottomSheet.dismiss()
                    }
                    else -> Unit
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) = Unit
        })
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveDate()
        bindingView()
        setUpTimePicker()
    }

    private fun bindingView() = with(binding) {
        viewModel.localDateSelect.value?.let { dateView.bindView(it) }
        buttonDone.setOnClickListener {
            resultSelectDate = ResultSelectDate(
                day = date.toLocalDate(),
                time = widgetTimePicker.getCurrentSelectTime()
            )

            onClickDone(resultSelectDate)
            this@SelectDateBottomSheet.dismiss()
        }
        iconExit.setOnClickListener {
            onClose()
            this@SelectDateBottomSheet.dismiss()
        }
    }

    private fun observeLiveDate() {
        val rvDates = binding.rvDates
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvDates)
        rvDates.adapter = dateAdapter

        viewModel.dateDetailsList.observe(this) { integerPagingData ->
            dateAdapter.submitData(lifecycle, integerPagingData)
        }

        viewModel.selectedDate.observe(this) { selectedDate ->
            val dateDetailsUI = selectedDate.toDate()?.toDateDetails()
            dateDetailsUI?.let {
                setSelectedDate(it)
            }
        }

        viewModel.resetDateList.observe(this) { timeInMillis ->
            if (timeInMillis != null) {
                dateAdapter.submitData(lifecycle, PagingData.empty())
                viewModel.setResetDate(null)
            }
        }

        viewModel.localTimeSelect.observe(this) {
            viewModel.setSelectTime(it)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setSelectedDate(dateDetailsUI: DateDetailsUI) {
        dateAdapter.setUpSelectDate(dateDetailsUI.dateKey)
        binding.dateView.bindDate(dateDetailsUI)
        date = dateDetailsUI.dateKey
    }

    private fun setUpTimePicker() {
        binding.widgetTimePicker.localTime.observe(this) {
            binding.dateView.bindTime(it)
        }
    }
}