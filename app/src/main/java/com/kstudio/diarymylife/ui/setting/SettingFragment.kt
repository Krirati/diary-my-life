package com.kstudio.diarymylife.ui.setting

import android.os.Bundle
import android.view.View
import com.kstudio.diarymylife.BuildConfig
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.FragmentSettingBinding
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.ui.setting.notification.NotificationActivity
import com.kstudio.diarymylife.ui.setting.profile.ProfileActivity
import com.kstudio.diarymylife.utils.External

class SettingFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingView()
    }

    override fun bindingView() {
        binding.apply {
            settingUsername.apply {
                setTitle(context.getString(R.string.profile))
                setIcon(R.drawable.ic_user)
                onWidgetClick { navigateToActivity(ProfileActivity::class.java) }
            }
            settingNoti.apply {
                setTitle(context.getString(R.string.notification))
                setIcon(R.drawable.ic_notification)
                onWidgetClick { navigateToActivity(NotificationActivity::class.java) }
            }

            settingPvp.apply {
                setTitle(context.getString(R.string.privacy_policy))
                setIcon(R.drawable.ic_file_minus)
                onWidgetClick {
                    External.openExternal(
                        requireContext(),
                        "https://www.termsfeed.com/live/c25e9145-a779-45c0-9a85-5cb393f0e635"
                    )
                }
            }

            settingLicense.apply {
                setTitle(context.getString(R.string.license))
                setIcon(R.drawable.ic_box)
                onWidgetClick {

                }
            }

            appVersion.apply {
                text = context.getString(R.string.version, BuildConfig.VERSION_NAME)
            }

            back.setOnClickListener { onBackPressedOrFinish() }
        }
    }

    override fun handleOnBackPress() {
        onBackPressedOrFinish()
    }
}
