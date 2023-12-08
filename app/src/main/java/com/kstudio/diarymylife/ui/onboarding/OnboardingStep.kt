package com.kstudio.diarymylife.ui.onboarding

sealed class OnboardingStep {
    object Start : OnboardingStep()
    object CreateAccount : OnboardingStep()
    object AcceptNotification : OnboardingStep()
    data class Error(val message: String) : OnboardingStep()
}
