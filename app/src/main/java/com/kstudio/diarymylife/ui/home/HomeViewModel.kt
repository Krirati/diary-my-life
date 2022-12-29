package com.kstudio.diarymylife.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kstudio.diarymylife.data.MoodItem
import com.kstudio.diarymylife.data.MoodUI
import com.kstudio.diarymylife.database.model.MoodWithActivity
import com.kstudio.diarymylife.repository.MoodRepository
import com.kstudio.diarymylife.ui.adapter.ItemCardMemoryAdapter.Companion.VIEW_ADD
import com.kstudio.diarymylife.ui.adapter.ItemCardMemoryAdapter.Companion.VIEW_ITEM
import com.kstudio.diarymylife.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class HomeViewModel constructor(
    private val moodRepository: MoodRepository
) : BaseViewModel() {

    companion object {
        const val GOOD_MORNING = "Good morning"
        const val GOOD_AFTERNOON = "Good afternoon"
        const val GOOD_EVENING = "Good evening"
        const val GOOD_NIGHT = "Good night"
    }

    private val _memberList: MutableLiveData<List<MoodItem>> = MutableLiveData()
    fun getMemberList(): LiveData<List<MoodItem>> = _memberList

    private val _welcomeText: MutableLiveData<String> = MutableLiveData()
    val welcomeText: LiveData<String> = _welcomeText

    fun fetchRecentJournal() {
        viewModelScope.launch {
            moodRepository.getMoodsAndActivitiesWithLimit().collect {
                _memberList.postValue(mapToUI(it))
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

    private fun mapToUI(list: List<MoodWithActivity>): List<MoodItem> {
        return if (list.isEmpty()) {
            listOf(
                MoodItem(
                    viewType = VIEW_ADD, data = null
                )
            )

        } else {
            list.map {
                MoodItem(
                    viewType = VIEW_ITEM,
                    data = MoodUI(
                        moodId = it.mood.moodId,
                        title = it.mood.title,
                        desc = it.mood.description,
                        mood = it.mood.mood,
                        activity = it.activities.asActivityDetail(),
                        timestamp = it.mood.timestamp,
                        imageId = "",
                    )
                )
            }

        }
    }
}