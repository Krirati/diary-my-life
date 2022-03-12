package com.kstudio.diarymylife.ui.widgets

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.ItemSelectDateBinding
import com.kstudio.diarymylife.model.DateDetailsUI
import com.kstudio.diarymylife.model.ResultSelectDate
import com.kstudio.diarymylife.model.toDateDetails
import com.kstudio.diarymylife.ui.adapter.DateSelectionAdapter
import com.kstudio.diarymylife.ui.write.WriteViewModel
import com.kstudio.diarymylife.utils.toDate
import com.kstudio.diarymylife.utils.toLocalDate
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class SelectDateBottomSheet @Inject constructor(
    private val getContext: Context,
    private val onClickDone: (ResultSelectDate) -> Unit,
) : BottomSheetDialogFragment() {

    private val parentView =
        LinearLayout.inflate(getContext, R.layout.item_select_date, null)
    private val binding by lazy { ItemSelectDateBinding.bind(parentView) }
    private val viewModel by viewModel<WriteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = binding.root

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    var resultSelectDate: ResultSelectDate = ResultSelectDate(LocalDate.now(), LocalDateTime.now())

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = object :
            BottomSheetDialog(getContext, theme) {
            override fun onBackPressed() {
                this@SelectDateBottomSheet.dismissAllowingStateLoss()
            }
        }
        dialog.behavior.state = BottomSheetBehavior.STATE_HIDDEN
        dialog.behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) dismiss()
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveDate()
        bindingView()
    }

    private fun bindingView() = with(binding) {
        buttonDone.setOnClickListener {
            onClickDone(resultSelectDate)
            dismissAllowingStateLoss()
        }
        iconExit.setOnClickListener { dismissAllowingStateLoss() }
    }

    private fun observeLiveDate() {
        val dateAdapter =
            DateSelectionAdapter(
                lifecycleOwner = this,
                initTime = viewModel.localDateTimeSelect.value.toString()
            ) { dateDetailsUI ->
                setSelectedDate(dateDetailsUI)
            }

        val rvDates = binding.rvDates
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rvDates)
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
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setSelectedDate(dateDetailsUI: DateDetailsUI) {
        binding.dateView.bindView(dateDetailsUI)
        resultSelectDate = ResultSelectDate(
            day = dateDetailsUI.dateKey.toLocalDate(),
            time = null
        )
    }
}