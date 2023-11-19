package com.kstudio.diarymylife.ui.onboarding.permission

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.FragmentOnboardingPageBinding
import com.kstudio.diarymylife.ui.base.BaseFragment
import com.kstudio.diarymylife.ui.onboarding.OnboardingStep
import com.kstudio.diarymylife.ui.onboarding.OnboardingViewModel

class PermissionFragment :
    BaseFragment<FragmentOnboardingPageBinding>(FragmentOnboardingPageBinding::inflate) {

    private val activityViewModel: OnboardingViewModel by activityViewModels()

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
        nextButton.setOnClickListener { activityViewModel.nextScreen(OnboardingStep.AcceptNotification) }
    }

    override fun handleOnBackPress() {
    }
}