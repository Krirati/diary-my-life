package com.kstudio.diarymylife.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kstudio.diarymylife.data.mood.MoodRepository
import com.kstudio.diarymylife.domain.GetMoodsAndActivitiesWithLimitUseCase
import com.kstudio.diarymylife.domain.ProfileUseCase
import com.kstudio.diarymylife.domain.model.MoodViewType
import com.kstudio.diarymylife.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class HomeViewModel constructor(
    private val moodRepository: MoodRepository,
    private val getMoodsAndActivitiesWithLimitUseCase: GetMoodsAndActivitiesWithLimitUseCase,
    private val profileUseCase: ProfileUseCase
) : BaseViewModel() {

    companion object {
        const val GOOD_MORNING = "Good morning"
        const val GOOD_AFTERNOON = "Good afternoon"
        const val GOOD_EVENING = "Good evening"
        const val GOOD_NIGHT = "Good night"
    }

    private val _memberList: MutableLiveData<List<MoodViewType>> = MutableLiveData()
    fun getMemberList(): LiveData<List<MoodViewType>> = _memberList

    private val _welcomeText: MutableLiveData<String> = MutableLiveData()
    val welcomeText: LiveData<String> = _welcomeText

    private val _nickname: MutableLiveData<String> = MutableLiveData<String>()
    val nickname: LiveData<String> = _nickname

    fun fetchRecentJournal() {
        viewModelScope.launch(Dispatchers.IO) {
            getMoodsAndActivitiesWithLimitUseCase.invoke().collect {
                _memberList.postValue(it)
            }
        }
    }

    fun deleteJournal(moodID: Long?) {
        if (moodID == null) return
        viewModelScope.launch {
            moodRepository.deleteMood(moodID = moodID)
        }
    }

    fun performWelcomeText() {
        val currentTime = LocalDateTime.now()
        when (currentTime.hour) {
            in 0..11 -> {
                _welcomeText.postValue(GOOD_MORNING)
            }

            in 12..15 -> {
                _welcomeText.postValue(GOOD_AFTERNOON)
            }

            in 16..20 -> {
                _welcomeText.postValue(GOOD_EVENING)
            }

            else -> {
                _welcomeText.postValue(GOOD_NIGHT)
            }
        }
    }

    fun getUserName() {
        viewModelScope.launch {
            _nickname.postValue(profileUseCase.getProfile().nickname)
        }
    }
}
