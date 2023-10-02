package com.kstudio.diarymylife.ui.onboarding

import android.os.Bundle
import androidx.navigation.findNavController
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.ActivityOnboardingBinding
import com.kstudio.diarymylife.ui.base.BaseActivity

class OnboardingActivity : BaseActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController.setGraph(R.navigation.onboarding_nav_graph)
    }
}