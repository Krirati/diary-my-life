package com.kstudio.diarymylife.ui.journal.journalEdit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kstudio.diarymylife.data.Journal
import com.kstudio.diarymylife.repository.JournalRepository
import com.kstudio.diarymylife.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class JournalEditViewModel(
    private val journalRepository: JournalRepository
) : BaseViewModel() {

    private var _journalDetail: MutableLiveData<Journal> = MutableLiveData()
    val journalData: MutableLiveData<Journal> = _journalDetail

    fun getJournalDetailFromID(id: Long?) {
        viewModelScope.launch {
            id?.let { it ->
                journalRepository.getJournalFromID(it)
                    .collect { journal ->
                        _journalDetail.postValue(journal)
                    }
            }
        }
    }

    fun updateJournal(journal: Journal) {
        viewModelScope.launch(Dispatchers.IO) {
            journalRepository.updateJournal(journal)
        }
    }
}