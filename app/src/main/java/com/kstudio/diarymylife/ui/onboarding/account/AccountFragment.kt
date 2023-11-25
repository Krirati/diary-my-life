package com.kstudio.diarymylife.ui.onboarding.account

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.FragmentOnboardingPageBinding
import com.kstudio.diarymylife.ui.base.BaseFragment
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
        description.text = getString(R.string.account_description)
        accountEditText.apply {
            isVisible = true
            setOnTextChange { s, _, _, count ->
                nextButton.isEnabled = count > 0
                activityViewModel.setOnAccountNickNameChange(s.toString())
            }
        }
        nextButton.apply {
            isEnabled = false
            setOnClickListener { activityViewModel.createAccount() }
        }
        return@with
    }

    override fun handleOnBackPress() {
    }
}