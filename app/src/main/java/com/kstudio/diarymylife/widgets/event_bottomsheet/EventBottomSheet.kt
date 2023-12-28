package com.kstudio.diarymylife.widgets.event_bottomsheet

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.window.OnBackInvokedDispatcher
import androidx.recyclerview.widget.ConcatAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.BottomSheetSelectEventBinding
import com.kstudio.diarymylife.domain.model.Event
import com.kstudio.diarymylife.widgets.event_bottomsheet.adapter.AddEventAdapter
import com.kstudio.diarymylife.widgets.event_bottomsheet.adapter.EventAdapter
import com.kstudio.diarymylife.widgets.event_bottomsheet.model.EventState
import org.koin.androidx.viewmodel.ext.android.viewModel
import javax.inject.Inject

class EventBottomSheet @Inject constructor(
    private val getContext: Context,
    private val onClose: (List<Event>?) -> Unit?,
) : BottomSheetDialogFragment() {

    private val parentView =
        LinearLayout.inflate(getContext, R.layout.bottom_sheet_select_event, null)

    private val binding by lazy { BottomSheetSelectEventBinding.bind(parentView) }
    private val viewModel by viewModel<EventViewModel>()
    private val eventsAdapter by lazy { EventAdapter(viewModel::updateEventSelectedListState) }
    private val addAdapter by lazy { AddEventAdapter() }
    private var concatAdapter = ConcatAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun getTheme(): Int = R.style.BaseBottomSheetDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = object : BottomSheetDialog(getContext, theme) {

            override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
                onClose(viewModel.eventSelectedListState.value)
                this@EventBottomSheet.dismissAllowingStateLoss()
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
                        onClose(viewModel.eventSelectedListState.value)
                        this@EventBottomSheet.dismiss()
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
        viewModel.getEventsList()
    }

    private fun bindingView() = with(binding) {
        iconExit.setOnClickListener {
            onClose(viewModel.eventSelectedListState.value)
            dismissAllowingStateLoss()
        }
        recyclerview.adapter = concatAdapter
    }

    private fun observeLiveDate() {
        viewModel.eventListState.observe(viewLifecycleOwner) {
            updateEventList(it)
        }
    }

    private fun updateEventList(events: List<EventState>) {
        events.forEach {
            when (it) {
                EventState.Add -> {
                    concatAdapter.addAdapter(addAdapter)
                }

                is EventState.Events -> {
                    eventsAdapter.updateEventList(it.events)
                    concatAdapter.addAdapter(eventsAdapter)
                }
            }
        }
    }
}
