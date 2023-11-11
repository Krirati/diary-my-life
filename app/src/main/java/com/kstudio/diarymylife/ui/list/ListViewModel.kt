package com.kstudio.diarymylife.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kstudio.diarymylife.data.mood.MoodRepository
import com.kstudio.diarymylife.domain.GetMoodsAndActivityUseCase
import com.kstudio.diarymylife.domain.model.MoodViewType
import com.kstudio.diarymylife.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class ListViewModel(
    private val moodRepository: MoodRepository,
    private val getMoodsAndActivityUseCase: GetMoodsAndActivityUseCase
) : BaseViewModel() {

    private val _memberList: MutableLiveData<List<MoodViewType>> = MutableLiveData()
    fun getMemberList(): LiveData<List<MoodViewType>> = _memberList

    fun fetchRecentJournal() {
        viewModelScope.launch {
            getMoodsAndActivityUseCase.invoke().collect {
                _memberList.postValue(it)
            }
        }
    }

    fun deleteJournal(moodID: Long?) {
        if (moodID == null) return
        viewModelScope.launch {
            moodRepository.deleteMood(moodID = moodID)
        }
    }
}
