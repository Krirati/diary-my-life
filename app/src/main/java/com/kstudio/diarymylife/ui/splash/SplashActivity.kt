package com.kstudio.diarymylife.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import com.kstudio.diarymylife.MainActivity
import com.kstudio.diarymylife.databinding.ActivitySplashBinding
import com.kstudio.diarymylife.ui.base.BaseActivity
import com.kstudio.diarymylife.ui.onboarding.OnboardingActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel by viewModel<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeLiveData()
        viewModel.checkAccount()
    }

    private fun observeLiveData() {
        viewModel.navigate.observe(this) {
            val activity = when (it) {
                Navigator.Home -> MainActivity::class.java
                Navigator.Onboarding -> OnboardingActivity::class.java
            }
            navigateToActivity(activity)
        }
    }
}
