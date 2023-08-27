package com.kstudio.diarymylife.ui.setting.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kstudio.diarymylife.domain.model.Profile
import com.kstudio.diarymylife.ui.base.BaseViewModel
import com.kstudio.diarymylife.utils.Gender
import com.kstudio.diarymylife.utils.toStringFormatApp
import java.time.LocalDate

class ProfileViewModel : BaseViewModel() {

    private val _isProfileChanged = MutableLiveData<Boolean>()
    val isProfileChanged: LiveData<Boolean> = _isProfileChanged

    private val _oldProfile = MutableLiveData<Profile>()

    private val _nickname = MutableLiveData<String>()

    private val _brithDate = MutableLiveData<String>()
    val brithDate: LiveData<String> = _brithDate

    private val _gender = MutableLiveData<String>()

    private fun updateProfileChanged() {
        when {
            _nickname.value != _oldProfile.value?.nickname || _gender.value != _oldProfile.value?.gender || _brithDate.value != _oldProfile.value?.birthDate -> {
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
        val date = LocalDate.of(year, month, day).toStringFormatApp()
        _brithDate.value = date ?: ""
        updateProfileChanged()
    }
}