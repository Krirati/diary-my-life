package com.kstudio.diarymylife.ui.home

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

class HomeViewModel constructor(
    private val moodRepository: MoodRepository
) : BaseViewModel() {

    private val _memberList: MutableLiveData<List<MoodItem>> = MutableLiveData()
    fun getMemberList() = _memberList

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