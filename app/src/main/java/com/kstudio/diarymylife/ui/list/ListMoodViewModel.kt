package com.kstudio.diarymylife.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kstudio.diarymylife.data.MoodItem
import com.kstudio.diarymylife.data.MoodUI
import com.kstudio.diarymylife.database.model.MoodWithActivity
import com.kstudio.diarymylife.repository.MoodRepository
import com.kstudio.diarymylife.ui.adapter.ItemCardMemoryAdapter
import com.kstudio.diarymylife.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class ListMoodViewModel constructor(
    private val moodRepository: MoodRepository
) : BaseViewModel() {

    private val _memberList: MutableLiveData<List<MoodItem>> = MutableLiveData()
    fun getMemberList(): LiveData<List<MoodItem>> = _memberList

    private val _averageMood: MutableLiveData<Long> = MutableLiveData(0)
    val averageMood: LiveData<Long> = _averageMood

    fun fetchRecentJournal() {
        viewModelScope.launch {
            moodRepository.getMoodsAndActivities().collect {
                _memberList.postValue(mapToUI(it))
                setMoodAverage(it)
            }
        }
    }


    private fun setMoodAverage(list: List<MoodWithActivity>) {
        var moodScore = 0L
        var average = 0L
        list.forEach { moodScore += it.mood.mood ?: 0 }
        if (list.isNotEmpty()) {
            average = moodScore / list.size
        }
        _averageMood.postValue(average)
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
                    viewType = ItemCardMemoryAdapter.VIEW_ADD, data = null
                )
            )

        } else {
            list.map {
                MoodItem(
                    viewType = ItemCardMemoryAdapter.VIEW_ITEM,
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