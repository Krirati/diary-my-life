package com.kstudio.diarymylife.ui.onboarding

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.kstudio.diarymylife.MainActivity
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.ActivityOnboardingBinding
import com.kstudio.diarymylife.ui.base.BaseActivity

class OnboardingActivity : BaseActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private val navController by lazy { findNavController(R.id.navHostOnboardingFragment) }
    private val viewModel: OnboardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController.setGraph(R.navigation.onboarding_nav_graph)
        observeLiveData()
    }


    private fun observeLiveData() {
        viewModel.onboardingStep.observe(this) {
            when (it) {
                OnboardingStep.CreateAccount -> {
                    navController.navigate(R.id.action_accountFragment_to_permissionFragment)
                }

                OnboardingStep.Start -> {
                    navController.navigate(R.id.action_welcomeFragment_to_accountFragment)
                }

                OnboardingStep.AcceptNotification-> {
                    navigateToActivity(MainActivity::class.java)
                }
            }
        }
    }


}