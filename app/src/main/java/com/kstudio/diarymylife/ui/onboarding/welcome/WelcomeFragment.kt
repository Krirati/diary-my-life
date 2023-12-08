package com.kstudio.diarymylife.ui.onboarding.welcome

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.FragmentOnboardingPageBinding
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.ui.onboarding.OnboardingStep
import com.kstudio.diarymylife.ui.onboarding.OnboardingViewModel

class WelcomeFragment :
    BaseFragment<FragmentOnboardingPageBinding>(FragmentOnboardingPageBinding::inflate) {

    private val activityViewModel by activityViewModels<OnboardingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleOnBackPress()
        bindingView()
    }

    override fun bindingView() = with(binding) {
        imagePage.setImageResource(R.drawable.crying)
        title.text = getString(R.string.welcome)
        description.text = getString(R.string.welcome_description)
        nextButton.setOnClickListener { activityViewModel.nextScreen(OnboardingStep.Start) }
    }

    override fun handleOnBackPress() {
    }
}
