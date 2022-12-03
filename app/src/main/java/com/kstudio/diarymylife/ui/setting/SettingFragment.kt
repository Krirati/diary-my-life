package com.kstudio.diarymylife.ui.setting

import android.os.Bundle
import android.view.View
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.FragmentSettingBinding
import com.kstudio.diarymylife.ui.base.BaseFragment

class SettingFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingView()
    }

    override fun bindingView() {
        binding.apply {
            settingUsername.apply {
                setTitle("Profile")
                setIcon(R.drawable.ic_user)
            }
            settingNoti.apply {
                setTitle("Notification")
                setIcon(R.drawable.ic_notification)
            }
            settingPrivacy.apply {
                setTitle("Privacy and security")
                setIcon(R.drawable.ic_shield)
            }
            settingCredit.apply {
                setTitle("Credit")
                setIcon(R.drawable.ic_credit_card)
            }
            settingClearData.apply {
                setTitle("Clear data")
                setIcon(R.drawable.ic_database)
            }
        }
    }

    override fun handleOnBackPress() {
        TODO("Not yet implemented")
    }

}