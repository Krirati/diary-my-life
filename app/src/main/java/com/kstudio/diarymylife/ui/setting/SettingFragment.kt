package com.kstudio.diarymylife.ui.setting

import android.os.Bundle
import android.view.View
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.FragmentSettingBinding
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.ui.setting.notification.NotificationActivity

class SettingFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {

    companion object {
        private const val PROFILE = "Profile"
        private const val NOTIFICATION = "Notification"
        private const val TERM_OF_SERVICE = "Term of service"
        private const val LICENSE_SUMMARY = "Open Source License"
        private const val CLEAR_DATA = "Clear data"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingView()
    }

    override fun bindingView() {
        binding.apply {
            settingUsername.apply {
                setTitle(PROFILE)
                setIcon(R.drawable.ic_user)
            }
            settingNoti.apply {
                setTitle(NOTIFICATION)
                setIcon(R.drawable.ic_notification)
                onWidgetClick { navigateToActivity(NotificationActivity::class.java) }
            }
            settingPrivacy.apply {
                setTitle(TERM_OF_SERVICE)
                setIcon(R.drawable.ic_shield)
            }
            settingCredit.apply {
                setTitle(LICENSE_SUMMARY)
                setIcon(R.drawable.ic_credit_card)
            }
            settingClearData.apply {
                setTitle(CLEAR_DATA)
                setIcon(R.drawable.ic_database)
            }
            appVersion.setOnClickListener { navigateToActivity(NotificationActivity::class.java) }
        }
    }

    override fun handleOnBackPress() {
        TODO("Not yet implemented")
    }
}