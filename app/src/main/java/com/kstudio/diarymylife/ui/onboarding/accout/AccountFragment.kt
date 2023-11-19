package com.kstudio.diarymylife.ui.onboarding.accout

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.FragmentOnboardingPageBinding
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.ui.onboarding.OnboardingStep
import com.kstudio.diarymylife.ui.onboarding.OnboardingViewModel

class AccountFragment :
    BaseFragment<FragmentOnboardingPageBinding>(FragmentOnboardingPageBinding::inflate) {

    private val activityViewModel by activityViewModels<OnboardingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleOnBackPress()
        bindingView()
    }

    override fun bindingView() = with(binding) {
        imagePage.setImageResource(R.drawable.crying)
        title.text = getString(R.string.create_your_account)
        description.text = getString(R.string.account_decription)
        accountEditText.isVisible = true
        nextButton.setOnClickListener { activityViewModel.nextScreen(OnboardingStep.CreateAccount) }
    }

    override fun handleOnBackPress() {
    }
}