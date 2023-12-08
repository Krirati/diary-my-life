package com.kstudio.diarymylife.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kstudio.diarymylife.data.mood.MoodRepository
import com.kstudio.diarymylife.domain.ProfileUseCase
import com.kstudio.diarymylife.ui.base.BaseMoodViewModel
import kotlinx.coroutines.launch

class CreateNewMoodViewModel(
    private val moodRepository: MoodRepository,
    private val profileUseCase: ProfileUseCase
) : BaseMoodViewModel() {

    private var _created: MutableLiveData<Unit> = MutableLiveData()
    var created: LiveData<Unit> = _created

    private var _nickname: MutableLiveData<String> = MutableLiveData()
    var nickname: LiveData<String> = _nickname

    init {
        viewModelScope.launch {
            val nickname = profileUseCase.getProfile().nickname
            _nickname.postValue(nickname)
        }
    }

    fun createNewMood() {
        viewModelScope.launch {
            val res = moodRepository.insert(setupCreateMoodRequest())
            _created.postValue(res)
        }
    }
}