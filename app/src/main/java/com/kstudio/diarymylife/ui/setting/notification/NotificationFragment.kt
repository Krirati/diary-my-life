package com.kstudio.diarymylife.ui.setting.notification

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.kstudio.diarymylife.data.ResultSelectDate
import com.kstudio.diarymylife.databinding.ActivityNotificationBinding
import com.kstudio.diarymylife.extensions.NotificationService
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.ui.setting.SettingNavigate
import com.kstudio.diarymylife.ui.setting.SettingViewModel
import com.kstudio.diarymylife.widgets.select_date_bottomsheet.SelectDateBottomSheet
import com.kstudio.diarymylife.widgets.select_date_bottomsheet.SelectDateHandle
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class NotificationFragment :
    BaseFragment<ActivityNotificationBinding>(ActivityNotificationBinding::inflate),
    SelectDateHandle {

    private val viewModel by viewModel<NotificationViewModel>()
    private val activityViewModel by activityViewModels<SettingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingView()
        observer()
    }

    override fun bindingView() = with(binding){
        back.setOnClickListener { handleOnBackPress() }
        switchEnableNotification.apply {
            setStateEnable(true)
            setOnSwitchCheckChange { viewModel.setIsEnableNotification(it) }
            setOnClickWidget { viewModel.setPerformBottomSheetSetTime() }
        }
        return@with
    }

    override fun handleOnBackPress() {
        activityViewModel.emitSettingNavigate(SettingNavigate.Setting)
    }

    private fun observer() {
        viewModel.apply {
            isEnableNotification.observe(viewLifecycleOwner) {
                if (it != null) {
                    binding.switchEnableNotification.setStateCheck(it)
                    activity?.let {activity ->
                        if (it == false) {
                            NotificationService(context = activity.applicationContext).cancelScheduleNotification()
                        } else {
                            NotificationService(context = activity.applicationContext).scheduleNotification(
                                viewModel.isDailyTimeChange.value?.hour ?: 10,
                                viewModel.isDailyTimeChange.value?.minute ?: 0
                            )
                        }
                    }
                }
            }

            isDailyTimeChange.observe(viewLifecycleOwner) {
                if (it != null) {
                    binding.switchEnableNotification.setSubtitle("Daily reminder time at $it")
                    if (isEnableNotification.value == true) {
                        activity?.let {activity ->
                            NotificationService(context = activity.applicationContext).scheduleNotification(
                                it.hour,
                                it.minute
                            )
                        }
                    }
                }
            }

            performBottomSheetSetTime.observe(viewLifecycleOwner) {
                showBottomSheetSelectTime()
            }
        }
    }

    private fun showBottomSheetSelectTime() {
        activity?.let {
            val bottomSheetSelectTime = SelectDateBottomSheet(
                getContext = it,
                onClickDone = ::onClickDoneBottomSheet,
                onClose = ::onCloseBottomSheet,
                type = SelectDateBottomSheet.BottomSheetType.DISPLAY_TIME,
                currentTimeSelected = LocalDateTime.of(
                    LocalDate.now(),
                    viewModel.isDailyTimeChange.value ?: LocalTime.now()
                )
            )
            bottomSheetSelectTime.show(
                it.supportFragmentManager,
                bottomSheetSelectTime.tag
            )
        }
    }

    override fun onClickDoneBottomSheet(date: ResultSelectDate) {
        viewModel.setIsDailyChange(date.time)
        viewModel.saveNotificationTime(date.time)
    }

    override fun onCloseBottomSheet() { /* Do nothing*/
    }
}
