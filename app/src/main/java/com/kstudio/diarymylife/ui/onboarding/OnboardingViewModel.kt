package com.kstudio.diarymylife.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OnboardingViewModel : ViewModel() {

    private val _onboardingStep = MutableLiveData<OnboardingStep>()
    val onboardingStep: LiveData<OnboardingStep> = _onboardingStep

    fun createAccount(name: String) {

    }

    fun checkPermission() {

    }

    private fun OnboardingStep.emitStep() {
        _onboardingStep.postValue(this)
    }

    fun nextScreen(step: OnboardingStep) {
        step.emitStep()
    }
}