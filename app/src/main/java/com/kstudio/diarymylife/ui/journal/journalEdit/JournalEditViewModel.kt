package com.kstudio.diarymylife.ui.journal.journalEdit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kstudio.diarymylife.entity.Mood
import com.kstudio.diarymylife.repository.JournalRepository
import com.kstudio.diarymylife.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class JournalEditViewModel(
    private val journalRepository: JournalRepository
) : BaseViewModel() {

    private var _journalDetail: MutableLiveData<Mood?> = MutableLiveData()
    val journalDetail: MutableLiveData<Mood?> = _journalDetail

    private var _journalNewDetail: MutableLiveData<Mood?> = MutableLiveData()
    val journalNewDetail: MutableLiveData<Mood?> = _journalNewDetail

    private val _event: MutableLiveData<Unit> = MutableLiveData()
    val event: LiveData<Unit> = _event

    fun getJournalDetailFromID(id: Long?) {
        viewModelScope.launch {
            id?.let { it ->
                journalRepository.getJournalFromID(it)
                    .collect { journal ->
                        _journalDetail.postValue(journal)
                        _journalNewDetail.postValue(journal)
                    }
            }
        }
    }

    fun updateJournal() {
        viewModelScope.launch(Dispatchers.IO) {
            val unit = journalRepository.updateJournal(createRequest())
            _event.postValue(unit)
        }
    }

    private fun createRequest(): Mood {
        return Mood(
            moodId = _journalDetail.value!!.moodId,
            title = _journalNewDetail.value?.title.orEmpty(),
            description = _journalNewDetail.value?.description.orEmpty(),
            mood = _journalNewDetail.value?.mood.orEmpty(),
            activity = _journalNewDetail.value?.activity,
            imageName = _journalNewDetail.value?.imageName,
            timestamp = _journalNewDetail.value!!.timestamp,
            createTime = _journalDetail.value!!.createTime,
        )
    }

    fun isEditJournal(): Boolean {
        if (_journalDetail.value == null) return false

        return _journalNewDetail.value != _journalDetail.value
    }

    fun setTitle(title: String) {
        _journalNewDetail.value = _journalNewDetail.value?.copy(title = title)
    }

    fun setDescription(description: String) {
        _journalNewDetail.value = _journalNewDetail.value?.copy(description = description)
    }

    fun setMood(mood: String) {
        _journalNewDetail.value = _journalNewDetail.value?.copy(mood = mood)
    }

    fun setActivities(activity: ArrayList<Int>) {
        _journalNewDetail.value = _journalNewDetail.value?.copy(activity = activity)
    }

    fun setTimestamp(timestamp: LocalDateTime) {
        _journalNewDetail.value = _journalNewDetail.value?.copy(timestamp = timestamp)
    }
}