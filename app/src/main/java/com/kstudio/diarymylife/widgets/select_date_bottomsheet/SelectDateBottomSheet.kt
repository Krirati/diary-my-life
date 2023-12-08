package com.kstudio.diarymylife.widgets.select_date_bottomsheet

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.window.OnBackInvokedDispatcher
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearSnapHelper
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.data.DateDetailsUI
import com.kstudio.diarymylife.data.ResultSelectDate
import com.kstudio.diarymylife.data.toDateDetails
import com.kstudio.diarymylife.databinding.BottomSheetSelectDateBinding
import com.kstudio.diarymylife.ui.adapter.dateSelection.DateSelectionAdapter
import com.kstudio.diarymylife.utils.toDateFormat
import com.kstudio.diarymylife.utils.toLocalDate
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

class SelectDateBottomSheet @Inject constructor(
    private val getContext: Context,
    private val onClickDone: (ResultSelectDate) -> Unit,
    private val onClose: () -> Unit?,
    private val type: BottomSheetType = BottomSheetType.DISPLAY_DATE_TIME,
    private val currentTimeSelected: LocalDateTime? = null
) : BottomSheetDialogFragment() {

    private val parentView =
        LinearLayout.inflate(getContext, R.layout.bottom_sheet_select_date, null)

    private var resultSelectDate: ResultSelectDate =
        ResultSelectDate(LocalDate.now(), LocalTime.now())

    private val binding by lazy { BottomSheetSelectDateBinding.bind(parentView) }
    private val dateAdapter by lazy { DateSelectionAdapter(this, ::setSelectedDate) }
    private val viewModel by viewModel<SelectDateBottomSheetViewModel>()
    private var date: String = ""

    enum class BottomSheetType {
        DISPLAY_DATE_TIME,
        DISPLAY_TIME
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun getTheme(): Int = R.style.BaseBottomSheetDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = object : BottomSheetDialog(getContext, theme) {

            override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
                this@SelectDateBottomSheet.dismissAllowingStateLoss()
                return super.getOnBackInvokedDispatcher()
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
        setInitTime()
        setDisplayType(type)
        observeLiveDate()
        bindingView()
        setUpTimePicker()
    }

    private fun bindingView() = with(binding) {
        viewModel.localDateSelect.value?.let { dateView.bindView(it) }
        viewModel.localTimeSelect.value?.let { dateView.bindTime(it) }
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
            val dateDetailsUI = selectedDate.toDateFormat()?.toDateDetails()
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

    private fun setDisplayType(type: BottomSheetType = BottomSheetType.DISPLAY_DATE_TIME) {
        when (type) {
            BottomSheetType.DISPLAY_DATE_TIME -> {
                displayBottomSheetDateTime()
            }

            BottomSheetType.DISPLAY_TIME -> {
                displayBottomSheetTime()
            }
        }
    }

    private fun setInitTime() {
        viewModel.setSelectDate(currentTimeSelected)
        binding.widgetTimePicker.setSelectTime(currentTimeSelected?.toLocalTime())
    }

    private fun displayBottomSheetDateTime() {
        binding.apply {
            titleBottomSheet.text = getString(R.string.select_date)
            dateView.visibility = View.VISIBLE
            rvDates.visibility = View.VISIBLE
        }
    }

    private fun displayBottomSheetTime() {
        binding.apply {
            titleBottomSheet.text = getString(R.string.select_time)
            dateView.visibility = View.GONE
            rvDates.visibility = View.GONE
        }
    }
}
