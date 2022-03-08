package com.kstudio.diarymylife.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kstudio.diarymylife.data.Journal
import com.kstudio.diarymylife.model.JournalItem
import com.kstudio.diarymylife.model.JournalUI
import com.kstudio.diarymylife.repository.JournalRepository
import com.kstudio.diarymylife.ui.adapter.ItemCardMemoryAdapter.Companion.VIEW_ADD
import com.kstudio.diarymylife.ui.adapter.ItemCardMemoryAdapter.Companion.VIEW_ITEM
import com.kstudio.diarymylife.ui.base.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
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
                Journal(
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
        viewModelScope.launch {
            journalRepository.getJournal()
                .onStart { Log.d("test", "flow onStart") }
                .onCompletion { Log.d("test", "flow onCompletion") }
                .catch {
                    Log.e("test", "flow catch: ${it.message}")
                }
                .collect {
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

    private fun mapToUI(list: List<Journal>): List<JournalItem> {
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
                        journalId = it.id,
                        title = it.title + it.id,
                        desc = it.description,
                        mood = it.mood ?: "",
                        activity = it.activity,
                        timestamp = it.timestamp,
                        imageId = "",
                    )
                )
            }

        }
    }

}