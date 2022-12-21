package com.kstudio.diarymylife.ui.setting.notification

import android.os.Bundle
import com.kstudio.diarymylife.databinding.ActivityNotificationBinding
import com.kstudio.diarymylife.ui.base.BaseActivity

class NotificationActivity : BaseActivity() {

    private lateinit var binding: ActivityNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding()
    }

    private fun binding() = with(binding) {
        back.setOnClickListener { handleBackPass() }
    }

    private fun handleBackPass() {
        onBackPressedDispatcher.onBackPressed()
    }
}