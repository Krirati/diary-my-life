package com.kstudio.diarymylife.ui.mood.moodEdit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kstudio.diarymylife.data.ActivityDetail
import com.kstudio.diarymylife.data.MoodItem
import com.kstudio.diarymylife.data.MoodUI
import com.kstudio.diarymylife.data.MoodRequest
import com.kstudio.diarymylife.database.model.MoodWithActivity
import com.kstudio.diarymylife.repository.MoodRepository
import com.kstudio.diarymylife.ui.adapter.ItemCardMemoryAdapter
import com.kstudio.diarymylife.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class JournalEditViewModel(
    private val moodRepository: MoodRepository
) : BaseViewModel() {

    private var _moodDetail: MutableLiveData<MoodItem?> = MutableLiveData()
    val moodDetail: MutableLiveData<MoodItem?> = _moodDetail

    private var _moodNewDetail: MutableLiveData<MoodItem?> = MutableLiveData()

    private var _isChanged: MutableLiveData<Boolean> = MutableLiveData()
    val isChanged: MutableLiveData<Boolean> = _isChanged

    private val _event: MutableLiveData<Unit> = MutableLiveData()
    val event: LiveData<Unit> = _event

    fun getJournalDetailFromID(id: Long?) {
        viewModelScope.launch {
            id?.let { it ->
                moodRepository.getMoodFromID(it)
                    .collect { journal ->
                        _moodDetail.postValue(mapToUI(journal))
                        _moodNewDetail.postValue(mapToUI(journal))
                    }
            }
        }
    }

    private fun mapToUI(mood: MoodWithActivity?): MoodItem? {
        return mood?.let {
            MoodItem(
                viewType = ItemCardMemoryAdapter.VIEW_ITEM,
                data = MoodUI(
                    moodId = it.mood.moodId,
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
            val unit = moodRepository.updateMood(createRequest())
            _event.postValue(unit)
        }
    }

    private fun createRequest(): MoodRequest? {
        val reqValue = _moodNewDetail.value?.data ?: return null
        val activityList = arrayListOf<ActivityDetail>()
        reqValue.activity?.forEach {
            activityList.add(it)
        }
        return MoodRequest(
            moodId = reqValue.moodId,
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
        if (_moodDetail.value == null) return false

        return _moodNewDetail.value?.data != _moodDetail.value?.data
    }

    private fun handlerIsChanged() {
        _isChanged.postValue(true)
        _isChanged.postValue(false)
    }
    fun setTitle(title: String) {
        handlerIsChanged()
        _moodNewDetail.value?.data = _moodNewDetail.value?.data?.copy(title = title)
    }

    fun setDescription(description: String) {
        handlerIsChanged()
        _moodNewDetail.value?.data = _moodNewDetail.value?.data?.copy(desc = description)
    }

    fun setMood(mood: String) {
        handlerIsChanged()
        _moodNewDetail.value?.data = _moodNewDetail.value?.data?.copy(mood = mood)
    }

    fun setActivities(activity: List<ActivityDetail>) {
        handlerIsChanged()
        _moodNewDetail.value?.data = _moodNewDetail.value?.data?.copy(activity = activity)
    }

    fun setTimestamp(timestamp: LocalDateTime) {
        handlerIsChanged()
        _moodNewDetail.value?.data = _moodNewDetail.value?.data?.copy(timestamp = timestamp)
    }
}