package com.kstudio.diarymylife.ui.journal.journalLanding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kstudio.diarymylife.data.Journal
import com.kstudio.diarymylife.repository.JournalRepository
import com.kstudio.diarymylife.ui.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class JournalLandingViewModel(
    private val journalRepository: JournalRepository
) : BaseViewModel() {

    private var _journalDetail: MutableLiveData<Journal> = MutableLiveData()
    val journalData: MutableLiveData<Journal> = _journalDetail

    fun getJournalDetailFromID(id: Long) {
        viewModelScope.launch {
            journalRepository.getJournalFromID(id)
                .collect {
                    _journalDetail.postValue(it)
                }

        }
    }
}