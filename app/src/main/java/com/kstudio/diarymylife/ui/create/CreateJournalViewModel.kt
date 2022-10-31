package com.kstudio.diarymylife.ui.create

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kstudio.diarymylife.data.MoodRequest
import com.kstudio.diarymylife.data.ActivityDetail
import com.kstudio.diarymylife.data.ResultSelectDate
import com.kstudio.diarymylife.repository.JournalRepository
import com.kstudio.diarymylife.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime


class CreateJournalViewModel(
    private val journalRepository: JournalRepository
) : BaseViewModel() {
    private var _selectsMood: MutableLiveData<Pair<Int, Int>> = MutableLiveData()
    var selectsMood: MutableLiveData<Pair<Int, Int>> = _selectsMood

    private var _selectsActivity: MutableLiveData<ArrayList<ActivityDetail>?> =
        MutableLiveData()
    var selectsActivity: MutableLiveData<ArrayList<ActivityDetail>?> = _selectsActivity

    private var _titleJournal: MutableLiveData<String> = MutableLiveData()

    private var _descJournal: MutableLiveData<String?> = MutableLiveData()

    fun setupSelectMood(index: Pair<Int, Int>) {
        _selectsMood.postValue(index)
    }

    fun setUpTitleJournal(text: String) {
        _titleJournal.postValue(text)
    }

    fun setUpDescJournal(text: String?) {
        _descJournal.postValue(text)
    }

    fun addSelectActivity(data: ActivityDetail) {
        val temp = _selectsActivity.value
        if (temp != null && !temp.contains(data)) {
            temp.add(data)
            _selectsActivity.postValue(temp)
        } else {
            _selectsActivity.postValue(arrayListOf(data))
        }
    }

    fun removeSelectActivity(data: ActivityDetail) {
        val temp = _selectsActivity.value
        if (_selectsActivity.value?.contains(data) == true) {
            temp?.remove(data)
            _selectsActivity.postValue(temp!!)
        } else return
    }

    fun createRecentJournal() {
        val activityList= arrayListOf<ActivityDetail>()
        _selectsActivity.value?.forEach {
            activityList.add(it)
        }
        val time = ResultSelectDate(localDateSelect.value, localTimeSelect.value)
        val req = MoodRequest(
            title = _titleJournal.value.orEmpty(),
            description = _descJournal.value.orEmpty(),
            timestamp = time.getLocalDateTime(),
            createTime = LocalDateTime.now(),
            imageName = _selectsMood.value?.second.toString(),
            mood = _selectsMood.value?.first.toString(),
            activity = activityList
        )
        viewModelScope.launch {
            journalRepository.insert(req)
        }
    }
}