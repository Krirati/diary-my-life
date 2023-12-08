package com.kstudio.diarymylife.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kstudio.diarymylife.data.shared_preferences.SharedPreferencesRepository
import com.kstudio.diarymylife.ui.base.BaseViewModel

class SplashViewModel(
    private val appPref: SharedPreferencesRepository
) : BaseViewModel() {

    private val _navigate = MutableLiveData<Navigator>()
    val navigate: LiveData<Navigator> = _navigate

    fun checkAccount() {
        if (appPref.hasAccount) {
            Navigator.Home.emit()
        } else {
            Navigator.Onboarding.emit()
        }
    }

    private fun Navigator.emit() {
        _navigate.postValue(this)
    }

}