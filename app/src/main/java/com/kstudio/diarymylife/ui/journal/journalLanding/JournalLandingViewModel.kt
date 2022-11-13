package com.kstudio.diarymylife.ui.journal.journalLanding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kstudio.diarymylife.data.JournalItem
import com.kstudio.diarymylife.data.JournalUI
import com.kstudio.diarymylife.database.model.MoodWithActivity
import com.kstudio.diarymylife.repository.JournalRepository
import com.kstudio.diarymylife.ui.adapter.ItemCardMemoryAdapter
import com.kstudio.diarymylife.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class JournalLandingViewModel(
    private val journalRepository: JournalRepository
) : BaseViewModel() {

    private var _journalDetail: MutableLiveData<JournalItem?> = MutableLiveData()
    val journalData: MutableLiveData<JournalItem?> = _journalDetail

    fun getJournalDetailFromID(id: Long) {
        viewModelScope.launch {
            journalRepository.getJournalFromID(id)
                .collect {
                    _journalDetail.postValue(mapToUI(it))
                }

        }
    }

    private fun mapToUI(mood: MoodWithActivity?): JournalItem? {
        return mood?.let {
            JournalItem(
                viewType = ItemCardMemoryAdapter.VIEW_ITEM,
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
            ?: return null
    }
}