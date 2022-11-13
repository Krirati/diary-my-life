package com.kstudio.diarymylife.ui.journal.journalEdit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kstudio.diarymylife.data.ActivityDetail
import com.kstudio.diarymylife.data.JournalItem
import com.kstudio.diarymylife.data.JournalUI
import com.kstudio.diarymylife.data.MoodRequest
import com.kstudio.diarymylife.database.model.MoodWithActivity
import com.kstudio.diarymylife.repository.JournalRepository
import com.kstudio.diarymylife.ui.adapter.ItemCardMemoryAdapter
import com.kstudio.diarymylife.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class JournalEditViewModel(
    private val journalRepository: JournalRepository
) : BaseViewModel() {

    private var _journalDetail: MutableLiveData<JournalItem?> = MutableLiveData()
    val journalDetail: MutableLiveData<JournalItem?> = _journalDetail

    private var _journalNewDetail: MutableLiveData<JournalItem?> = MutableLiveData()
    val journalNewDetail: MutableLiveData<JournalItem?> = _journalNewDetail

    private var _isChanged: MutableLiveData<Boolean> = MutableLiveData()
    val isChanged: MutableLiveData<Boolean> = _isChanged

    private val _event: MutableLiveData<Unit> = MutableLiveData()
    val event: LiveData<Unit> = _event

    fun getJournalDetailFromID(id: Long?) {
        viewModelScope.launch {
            id?.let { it ->
                journalRepository.getJournalFromID(it)
                    .collect { journal ->
                        _journalDetail.postValue(mapToUI(journal))
                        _journalNewDetail.postValue(mapToUI(journal))
                    }
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

    fun updateJournal() {
        viewModelScope.launch(Dispatchers.IO) {
            val unit = journalRepository.updateJournal(createRequest())
            _event.postValue(unit)
        }
    }

    private fun createRequest(): MoodRequest? {
        val reqValue = _journalNewDetail.value?.data ?: return null
        val activityList = arrayListOf<ActivityDetail>()
        reqValue.activity?.forEach {
            activityList.add(it)
        }
        return MoodRequest(
            moodId = reqValue.journalId,
            title = reqValue.title,
            description = reqValue.desc,
            mood = reqValue.mood,
            activity = activityList,
            imageName = reqValue.imageId,
            timestamp = reqValue.timestamp,
            createTime = LocalDateTime.now(),
        )
    }

    fun isEditJournal(): Boolean {
        if (_journalDetail.value == null) return false

        return _journalNewDetail.value?.data != _journalDetail.value?.data
    }

    private fun handlerIsChanged() {
        _isChanged.postValue(true)
        _isChanged.postValue(false)
    }
    fun setTitle(title: String) {
        handlerIsChanged()
        _journalNewDetail.value?.data = _journalNewDetail.value?.data?.copy(title = title)
    }

    fun setDescription(description: String) {
        handlerIsChanged()
        _journalNewDetail.value?.data = _journalNewDetail.value?.data?.copy(desc = description)
    }

    fun setMood(mood: String) {
        handlerIsChanged()
        _journalNewDetail.value?.data = _journalNewDetail.value?.data?.copy(mood = mood)
    }

    fun setActivities(activity: List<ActivityDetail>) {
        handlerIsChanged()
        _journalNewDetail.value?.data = _journalNewDetail.value?.data?.copy(activity = activity)
    }

    fun setTimestamp(timestamp: LocalDateTime) {
        handlerIsChanged()
        _journalNewDetail.value?.data = _journalNewDetail.value?.data?.copy(timestamp = timestamp)
    }
}