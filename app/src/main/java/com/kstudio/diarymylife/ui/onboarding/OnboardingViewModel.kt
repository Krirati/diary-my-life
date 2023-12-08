package com.kstudio.diarymylife.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kstudio.diarymylife.data.base.Response
import com.kstudio.diarymylife.data.profile.Profile
import com.kstudio.diarymylife.data.shared_preferences.SharedPreferencesRepository
import com.kstudio.diarymylife.domain.ProfileUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class OnboardingViewModel(
    private val profileUseCase: ProfileUseCase,
    private val appPreferenceRepository: SharedPreferencesRepository,
) : ViewModel() {

    private val _onboardingStep = MutableLiveData<OnboardingStep>()
    val onboardingStep: LiveData<OnboardingStep> = _onboardingStep

    private val _isEnableNotification = MutableLiveData<Boolean>(true)

    private val _isDailyTimeChange = MutableLiveData(LocalTime.of(18, 0))
    val isDailyTimeChange: LiveData<LocalTime?> = _isDailyTimeChange

    private var accountName: String = ""
    private var gender: String = ""
    private var birthdate: LocalDate? = null

    fun createAccount() {
        if (accountName.isEmpty()) return

        val profile = Profile(
            profileId = 0L,
            nickname = accountName,
            gender = gender,
            birthDate = birthdate
        )
        viewModelScope.launch {
            when (profileUseCase.createProfile(profile)) {
                is Response.Success -> {
                    appPreferenceRepository.hasAccount = true
                    OnboardingStep.CreateAccount.emitStep()
                }

                Response.Failed -> {
                    OnboardingStep.Error("Cannot create profile").emitStep()
                }
            }
        }
    }

    private fun OnboardingStep.emitStep() {
        _onboardingStep.postValue(this)
    }

    fun nextScreen(step: OnboardingStep) {
        step.emitStep()
    }

    fun setOnAccountNickNameChange(nickname: String) {
        accountName = nickname
    }

    fun setIsEnableNotification(isEnable: Boolean) {
        _isEnableNotification.postValue(isEnable)
    }

    fun setIsDailyChange(time: LocalTime?) {
        _isDailyTimeChange.postValue(time)
    }

    fun setUpNotification() {
        appPreferenceRepository.enableNotification = _isEnableNotification.value ?: false
        appPreferenceRepository.dailyTime = _isDailyTimeChange.value.toString()
    }
}