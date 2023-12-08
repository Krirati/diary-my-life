package com.kstudio.diarymylife.ui.onboarding

import java.time.LocalTime

sealed class OnboardingStep {
    object Start : OnboardingStep()
    object CreateAccount : OnboardingStep()
    data class AcceptNotification(val localTime: LocalTime?) : OnboardingStep()
    object DoNotAcceptNotification : OnboardingStep()
    data class Error(val message: String) : OnboardingStep()
}
