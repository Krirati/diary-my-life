package com.kstudio.diarymylife.ui.onboarding

import android.os.Bundle
import android.widget.Toast
import androidx.navigation.findNavController
import com.kstudio.diarymylife.MainActivity
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.ActivityOnboardingBinding
import com.kstudio.diarymylife.ui.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnboardingActivity : BaseActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private val navController by lazy { findNavController(R.id.navHostOnboardingFragment) }
    private val viewModel by viewModel<OnboardingViewModel>()

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

                OnboardingStep.AcceptNotification -> {
                    navigateToActivity(MainActivity::class.java)
                }

                is OnboardingStep.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}