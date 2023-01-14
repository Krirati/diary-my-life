package com.kstudio.diarymylife.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kstudio.diarymylife.data.mood.MoodRepository
import com.kstudio.diarymylife.domain.GetMoodsAndActivitiesWithLimitUseCase
import com.kstudio.diarymylife.domain.model.MoodViewType
import com.kstudio.diarymylife.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class ListMoodViewModel constructor(
    private val moodRepository: MoodRepository,
    private val getMoodsAndActivitiesWithLimitUseCase: GetMoodsAndActivitiesWithLimitUseCase
) : BaseViewModel() {

    private val _memberList: MutableLiveData<List<MoodViewType>> = MutableLiveData()
    fun getMemberList(): LiveData<List<MoodViewType>> = _memberList

    private val _averageMood: MutableLiveData<Long> = MutableLiveData(0)
    val averageMood: LiveData<Long> = _averageMood

    fun fetchRecentJournal() {
        viewModelScope.launch {
            getMoodsAndActivitiesWithLimitUseCase.invoke().collect {
                _memberList.postValue(it)
                setMoodAverage(it)
            }
        }
    }


    private fun setMoodAverage(list: List<MoodViewType>) {
        var moodScore = 0L
        var average = 0L
        list.forEach { moodScore += it.data?.mood ?: 0 }
        if (list.isNotEmpty()) {
            average = moodScore / list.size
        }
        _averageMood.postValue(average)
    }

    fun deleteJournal(moodID: Long?) {
        if (moodID == null) return
        viewModelScope.launch {
            moodRepository.deleteMood(moodID = moodID)
        }
    }
}