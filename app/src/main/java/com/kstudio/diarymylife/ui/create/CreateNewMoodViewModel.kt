package com.kstudio.diarymylife.ui.create

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kstudio.diarymylife.data.MoodRequest
import com.kstudio.diarymylife.data.ResultSelectDate
import com.kstudio.diarymylife.data.mood.MoodRepository
import com.kstudio.diarymylife.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class CreateNewMoodViewModel(
    private val moodRepository: MoodRepository
) : BaseViewModel() {

    private var _created: MutableLiveData<Unit> = MutableLiveData()
    var created: LiveData<Unit> = _created

    private var _selectsMood: MutableLiveData<Pair<Int, Int>> = MutableLiveData()
    private val _localDateSelect: MutableLiveData<LocalDate> = MutableLiveData(LocalDate.now())
    private val _localTimeSelect: MutableLiveData<LocalTime> = MutableLiveData(LocalTime.now())
    private val _moodDesc: MutableLiveData<String> = MutableLiveData()
    private val _imageUrl: MutableLiveData<Uri?> = MutableLiveData()

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
        viewModelScope.launch {
            val res = moodRepository.insert(setupMoodRequest())
            _created.postValue(res)
        }
    }

    fun setMoodDesc(desc: String) {
        _moodDesc.postValue(desc)
    }

    fun setImageUri(uri: Uri?) {
        _imageUrl.value = uri
    }

    private fun setupMoodRequest(): MoodRequest {
        val time = ResultSelectDate(_localDateSelect.value, _localTimeSelect.value)

        return MoodRequest(
            moodId = null,
            mood = _selectsMood.value?.first,
            description = _moodDesc.value ?: "",
            timestamp = time.getLocalDateTime(),
            createTime = LocalDateTime.now(),
            imageName = _selectsMood.value?.second.toString(),
            activity = arrayListOf(),
            uri = _imageUrl.value
        )
    }
}