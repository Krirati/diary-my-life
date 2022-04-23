package com.kstudio.diarymylife.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kstudio.diarymylife.data.MoodRequest
import com.kstudio.diarymylife.entity.relations.MoodWithActivity
import com.kstudio.diarymylife.data.JournalItem
import com.kstudio.diarymylife.data.JournalUI
import com.kstudio.diarymylife.repository.JournalRepository
import com.kstudio.diarymylife.ui.adapter.ItemCardMemoryAdapter.Companion.VIEW_ADD
import com.kstudio.diarymylife.ui.adapter.ItemCardMemoryAdapter.Companion.VIEW_ITEM
import com.kstudio.diarymylife.ui.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class HomeViewModel constructor(
    private val journalRepository: JournalRepository
) : BaseViewModel() {

    private val _memberList: MutableLiveData<List<JournalItem>> = MutableLiveData()
    fun getMemberList() = _memberList

    fun createRecentJournal() {
        viewModelScope.launch {
            journalRepository.insert(
                MoodRequest(
                    title = "nine",
                    description = "erre",
                    timestamp = LocalDateTime.now(),
                    createTime = LocalDateTime.now(),
                    imageName = "",
                    mood = ""
                )
            )
        }
    }

    fun fetchRecentJournal() {
//        viewModelScope.launch {
//            journalRepository.getJournal()
//                .onStart { }
//                .onCompletion { }
//                .catch { }
//                .collect {
//                    _memberList.postValue(mapToUI(it))
//                }
//        }

        viewModelScope.launch {
            journalRepository.getMoodsAndActivities().collect {
                Log.d("test", "it ::" + it)
                _memberList.postValue(mapToUI(it))
            }
        }


    }


    fun deleteJournal(journalID: Long?) {
        if (journalID == null) return
        viewModelScope.launch {
            journalRepository.deleteJournal(journalID = journalID)
        }
    }

    private fun mapToUI(list: List<MoodWithActivity>): List<JournalItem> {
        return if (list.isEmpty()) {
            listOf(
                JournalItem(
                    viewType = VIEW_ADD, data = null
                )
            )

        } else {
            list.map {
                JournalItem(
                    viewType = VIEW_ITEM,
                    data = JournalUI(
                        journalId = it.mood.moodId,
                        title = it.mood.title,
                        desc = it.mood.description,
                        mood = it.mood.mood ?: "",
                        activity = it.activities.asActivityDetail(),
                        timestamp = it.mood.timestamp,
                        imageId = "",
                    )
                )
            }

        }
    }
}