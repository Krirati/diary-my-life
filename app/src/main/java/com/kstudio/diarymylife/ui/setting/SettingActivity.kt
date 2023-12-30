package com.kstudio.diarymylife.ui.setting

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.ActivitySettingBinding
import com.kstudio.diarymylife.ui.base.BaseActivity

class SettingActivity : BaseActivity() {
    private lateinit var binding: ActivitySettingBinding

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }
    private val viewModel by viewModels<SettingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController.setGraph(R.navigation.setting_nav_graph)

        observerNavigate()
    }

    private fun observerNavigate() {
        viewModel.navigateEvent.observe(this) {
            when (it) {
                SettingNavigate.License -> navController.navigate(R.id.action_settingFragment_to_licenceFragment)
                SettingNavigate.Notification -> navController.navigate(R.id.action_settingFragment_to_notificationFragment)
                SettingNavigate.Profile -> navController.navigate(R.id.action_settingFragment_to_profileFragment)
                SettingNavigate.Setting -> navController.navigateUp()
            }
        }
    }
}
