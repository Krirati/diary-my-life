package com.kstudio.diarymylife.ui.create.select_mood

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kstudio.diarymylife.data.MoodRequest
import com.kstudio.diarymylife.data.ResultSelectDate
import com.kstudio.diarymylife.data.mood.MoodRepository
import com.kstudio.diarymylife.ui.base.BaseViewModel
import com.kstudio.diarymylife.utils.mapMoodStringToTitle
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class SelectMoodViewModel(
    private val moodRepository: MoodRepository
) : BaseViewModel() {

    private var _created: MutableLiveData<Unit> = MutableLiveData()
    var created: LiveData<Unit> = _created

    private var _selectsMood: MutableLiveData<Pair<Int, Int>> = MutableLiveData()

    private val _localDateSelect: MutableLiveData<LocalDate> = MutableLiveData(LocalDate.now())
    val localDateSelect: MutableLiveData<LocalDate> = _localDateSelect

    private val _localTimeSelect: MutableLiveData<LocalTime> = MutableLiveData(LocalTime.now())
    val localTimeSelect: MutableLiveData<LocalTime> = _localTimeSelect

    fun setupSelectMood(index: Pair<Int, Int>) {
        _selectsMood.postValue(index)
    }

    fun setSelectDate(localDateTime: LocalDate) {
        _localDateSelect.postValue(localDateTime)
    }

    fun setSelectTime(localTime: LocalTime) {
        _localTimeSelect.postValue(localTime)
    }

    fun createMood() {
        val time = ResultSelectDate(localDateSelect.value, localTimeSelect.value)
        val req = MoodRequest(
            moodId = null,
            title = mapMoodStringToTitle(_selectsMood.value?.first),
            description = "",
            timestamp = time.getLocalDateTime(),
            createTime = LocalDateTime.now(),
            imageName = _selectsMood.value?.second.toString(),
            mood = _selectsMood.value?.first,
            activity = arrayListOf()
        )
        viewModelScope.launch {
            val res = moodRepository.insert(req)
            _created.postValue(res)
        }
    }
}