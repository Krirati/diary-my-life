package com.kstudio.diarymylife.ui.setting.notification

import android.os.Bundle
import com.kstudio.diarymylife.data.ResultSelectDate
import com.kstudio.diarymylife.databinding.ActivityNotificationBinding
import com.kstudio.diarymylife.extensions.NotificationService
import com.kstudio.diarymylife.ui.base.BaseActivity
import com.kstudio.diarymylife.widgets.select_date_bottomsheet.SelectDateBottomSheet
import com.kstudio.diarymylife.widgets.select_date_bottomsheet.SelectDateHandle
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class NotificationActivity : BaseActivity(), SelectDateHandle {

    private lateinit var binding: ActivityNotificationBinding
    private val viewModel by viewModel<NotificationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding()
        observer()
    }

    private fun binding() = with(binding) {
        back.setOnClickListener { handleBackPass() }
        switchEnableNotification.apply {
            setStateEnable(true)
            setOnSwitchCheckChange { viewModel.setIsEnableNotification(it) }
            setOnClickWidget { viewModel.setPerformBottomSheetSetTime() }
        }
    }

    private fun observer() {
        viewModel.apply {
            isEnableNotification.observe(this@NotificationActivity) {
                if (it != null) {
                    binding.switchEnableNotification.setStateCheck(it)
                    if (it == false) {
                        NotificationService(context = applicationContext).cancelScheduleNotification()
                    } else {
                        NotificationService(context = applicationContext).scheduleNotification(
                            viewModel.isDailyTimeChange.value?.hour ?: 10,
                            viewModel.isDailyTimeChange.value?.minute ?: 0
                        )
                    }
                }
            }

            isDailyTimeChange.observe(this@NotificationActivity) {
                if (it != null) {
                    binding.switchEnableNotification.setSubtitle("Daily reminder time at $it")
                    if (isEnableNotification.value == true) {
                        NotificationService(context = applicationContext).scheduleNotification(
                            it.hour,
                            it.minute
                        )
                    }
                }
            }

            performBottomSheetSetTime.observe(this@NotificationActivity) {
                showBottomSheetSelectTime()
            }
        }
    }


    private fun showBottomSheetSelectTime() {
        val bottomSheetSelectTime = SelectDateBottomSheet(
            getContext = this@NotificationActivity,
            onClickDone = ::onClickDoneBottomSheet,
            onClose = ::onCloseBottomSheet,
            type = SelectDateBottomSheet.BottomSheetType.DISPLAY_TIME,
            currentTimeSelected = LocalDateTime.of(
                LocalDate.now(),
                viewModel.isDailyTimeChange.value ?: LocalTime.now()
            )
        )
        bottomSheetSelectTime.show(
            this@NotificationActivity.supportFragmentManager,
            bottomSheetSelectTime.tag
        )
    }

    override fun onClickDoneBottomSheet(date: ResultSelectDate) {
        viewModel.setIsDailyChange(date.time)
        viewModel.saveNotificationTime(date.time)
    }

    override fun onCloseBottomSheet() { /* Do nothing*/
    }

}