package com.kstudio.diarymylife.ui.setting

import android.os.Bundle
import android.view.View
import com.kstudio.diarymylife.BuildConfig
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.FragmentSettingBinding
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.ui.setting.notification.NotificationActivity
import com.kstudio.diarymylife.ui.setting.profile.ProfileActivity

class SettingFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {

    companion object {
        private const val PROFILE = "Profile"
        private const val NOTIFICATION = "Notification"
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
                onWidgetClick { navigateToActivity(ProfileActivity::class.java) }
            }
            settingNoti.apply {
                setTitle(NOTIFICATION)
                setIcon(R.drawable.ic_notification)
                onWidgetClick { navigateToActivity(NotificationActivity::class.java) }
            }

            appVersion.apply {
                text = context.getString(R.string.version, BuildConfig.VERSION_NAME)
                setOnClickListener { navigateToActivity(NotificationActivity::class.java) }
            }

            back.setOnClickListener { onBackPressedOrFinish() }
        }
    }

    override fun handleOnBackPress() {
        TODO("Not yet implemented")
    }
}