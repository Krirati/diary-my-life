package com.kstudio.diarymylife.ui.setting.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kstudio.diarymylife.data.profile.Profile
import com.kstudio.diarymylife.domain.ProfileUseCase
import com.kstudio.diarymylife.ui.base.BaseViewModel
import com.kstudio.diarymylife.utils.Gender
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class ProfileViewModel(
    private val profileUseCase: ProfileUseCase
) : BaseViewModel() {

    private val _isProfileChanged = MutableLiveData<Boolean>()
    val isProfileChanged: LiveData<Boolean> = _isProfileChanged

    private val _oldProfile = MutableLiveData<Profile>()
    val oldProfile: LiveData<Profile> = _oldProfile
    private val _nickname = MutableLiveData<String>()

    private val _brithDate = MutableLiveData<LocalDate>()
    val brithDate: LiveData<LocalDate> = _brithDate

    private val _gender = MutableLiveData<String>("")

    private val _event = MutableLiveData<Boolean>()
    val event: LiveData<Boolean> = _event

    init {
        getCurrentProfile()
    }

    private fun getCurrentProfile() {
        viewModelScope.launch {
            val result = profileUseCase.getProfile()
            _oldProfile.value = result
        }
    }

    private fun updateProfileChanged() {
        when {
            _nickname.value != _oldProfile.value?.nickname ||
                    _gender.value != _oldProfile.value?.gender ||
                    (_brithDate.value != null && _brithDate.value != _oldProfile.value?.birthDate) -> {
                _isProfileChanged.postValue(true)
            }

            else -> _isProfileChanged.postValue(false)
        }
    }

    fun setGender(gender: Gender) {
        _gender.value = gender.gender
        updateProfileChanged()
    }

    fun setNickname(nickname: String) {
        _nickname.value = nickname
        updateProfileChanged()
    }

    fun setBirthDate(year: Int, month: Int, day: Int) {
        val date = LocalDate.of(year, month + 1, day)
        _brithDate.value = date
        updateProfileChanged()
    }

    fun updateProfile() {
        val profileId = _oldProfile.value?.profileId ?: return
        viewModelScope.launch(Dispatchers.IO) {
            val profile = Profile(
                profileId = profileId,
                nickname = _nickname.value ?: "",
                birthDate = _brithDate.value,
                gender = _gender.value ?: ""
            )
            profileUseCase.updateProfile(profile)
            _event.postValue(true)
        }
    }
}
