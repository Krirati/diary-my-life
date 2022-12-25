package com.kstudio.diarymylife.ui.setting.notification

import android.os.Bundle
import com.kstudio.diarymylife.data.ResultSelectDate
import com.kstudio.diarymylife.databinding.ActivityNotificationBinding
import com.kstudio.diarymylife.ui.base.BaseActivity
import com.kstudio.diarymylife.widgets.select_date_bottomsheet.SelectDateBottomSheet
import com.kstudio.diarymylife.widgets.select_date_bottomsheet.SelectDateHandle
import org.koin.androidx.viewmodel.ext.android.viewModel

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
        }
        switchDaily.apply {
            setOnSwitchCheckChange { viewModel.setIsDailyNotification(it) }
            setOnClickWidget { showBottomSheetSelectTime() }
        }
    }

    private fun observer() {
        viewModel.apply {
            isEnableNotification.observe(this@NotificationActivity) {
                binding.switchDaily.setStateEnable(it)
                // set up notification enable
            }

            isDailyNotification.observe(this@NotificationActivity) {
                // set up notification daily
            }

            isDailyChange.observe(this@NotificationActivity) {
                binding.switchDaily.setSubtitle(it.toString())
            }
        }
    }

    private fun handleBackPass() {
        onBackPressedDispatcher.onBackPressed()
    }

    private fun showBottomSheetSelectTime() {
        val bottomSheetSelectTime = SelectDateBottomSheet(
            this@NotificationActivity,
            ::onClickDoneBottomSheet,
            ::onCloseBottomSheet,
        )
        bottomSheetSelectTime.setDisplayType(SelectDateBottomSheet.BottomSheetType.DISPLAY_TIME)
        bottomSheetSelectTime.show(
            this@NotificationActivity.supportFragmentManager,
            bottomSheetSelectTime.tag
        )
    }

    override fun onClickDoneBottomSheet(date: ResultSelectDate) {
        viewModel.setIsDailyChange(date.time)
    }

    override fun onCloseBottomSheet() {
    }
}