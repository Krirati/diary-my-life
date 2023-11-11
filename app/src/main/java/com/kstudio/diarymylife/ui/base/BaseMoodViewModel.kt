package com.kstudio.diarymylife.ui.base

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.kstudio.diarymylife.data.MoodItem
import com.kstudio.diarymylife.data.MoodRequest
import com.kstudio.diarymylife.data.MoodUI
import com.kstudio.diarymylife.data.ResultSelectDate
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

open class BaseMoodViewModel() : BaseViewModel() {

    private var _selectsMood: MutableLiveData<Pair<Int, Int>> = MutableLiveData()

    private val _localDateSelect: MutableLiveData<LocalDate> = MutableLiveData(LocalDate.now())
    private val _localTimeSelect: MutableLiveData<LocalTime> = MutableLiveData(LocalTime.now())
    private val _moodTitle: MutableLiveData<String> = MutableLiveData()
    private val _moodDesc: MutableLiveData<String> = MutableLiveData()
    private val _imageUrl: MutableLiveData<Uri?> = MutableLiveData()
    private var _moodDetail: MutableLiveData<MoodItem?> = MutableLiveData()
    val moodData: MutableLiveData<MoodItem?> = _moodDetail

    fun setupSelectMood(index: Pair<Int, Int>) {
        _selectsMood.postValue(index)
    }

    fun setSelectDate(localDateTime: LocalDate) {
        _localDateSelect.postValue(localDateTime)
    }

    fun setSelectTime(localTime: LocalTime) {
        _localTimeSelect.postValue(localTime)
    }

    fun setMoodTitle(desc: String) {
        _moodTitle.postValue(desc)
    }

    fun setMoodDesc(desc: String) {
        _moodDesc.postValue(desc)
    }

    fun setImageUri(uri: Uri?) {
        _imageUrl.value = uri
    }

    fun setUpInitDetail(mood: MoodUI) {
        _localDateSelect.value = mood.timestamp.toLocalDate()
        _localTimeSelect.value = mood.timestamp.toLocalTime()
        _moodDesc.value = mood.desc
    }

    fun setUpMoodDetail(mood: MoodItem?) {
        _moodDetail.postValue(mood)
    }

    fun setupCreateMoodRequest(): MoodRequest {
        val time = ResultSelectDate(_localDateSelect.value, _localTimeSelect.value)

        return MoodRequest(
            moodId = null,
            mood = _selectsMood.value?.first,
            title = _moodTitle.value ?: "",
            description = _moodDesc.value ?: "",
            timestamp = time.getLocalDateTime(),
            createTime = LocalDateTime.now(),
            imageName = _selectsMood.value?.second.toString(),
            activity = arrayListOf(),
            uri = _imageUrl.value
        )
    }

    fun setupUpdateMoodRequest(): MoodRequest {
        val time = ResultSelectDate(_localDateSelect.value, _localTimeSelect.value)
        val moodId = _moodDetail.value?.data?.moodId
        return MoodRequest(
            moodId = moodId,
            mood = _selectsMood.value?.first,
            title = _moodTitle.value ?: "",
            description = _moodDesc.value ?: "",
            timestamp = time.getLocalDateTime(),
            createTime = LocalDateTime.now(),
            imageName = _selectsMood.value?.second.toString(),
            activity = arrayListOf(),
            uri = _imageUrl.value,
            fileName = _moodDetail.value?.data?.fileName
        )
    }

    fun getLocalDateTime(): LocalDateTime? {
        return _localDateSelect.value?.atTime(_localTimeSelect.value)
    }
}
