package com.kstudio.diarymylife.ui.home

import androidx.lifecycle.MutableLiveData
import com.kstudio.diarymylife.model.JournalCard
import com.kstudio.diarymylife.ui.base.BaseViewModel
import java.sql.Timestamp

class HomeViewModel : BaseViewModel() {

    private val _memberList: MutableLiveData<ArrayList<JournalCard>> = MutableLiveData()
    fun getMemberList() = _memberList

    fun fetchMemberList() {
        val card = JournalCard(
            journalId = "0",
            title = "nine",
            desc = "desc",
            timestamp = Timestamp(System.currentTimeMillis()),
            mood = "good",
            imageId = "test",
            activity = arrayListOf("1", "2", "3")
        )
        val card2 = JournalCard(
            journalId = "1",
            title = "nine2",
            desc = "desc",
            timestamp = Timestamp(3333),
            mood = "good",
            imageId = "test",
            activity = null
        )
        val card3 = JournalCard(
            journalId = "2",
            title = "nine3",
            desc = "desc",
            timestamp = Timestamp(4444),
            mood = "good",
            imageId = "test",
            activity = arrayListOf("1", "2", "3")
        )

        val members = arrayListOf(card, card2, card3)
        members.sortBy { it.timestamp }
        val memberList: ArrayList<JournalCard> = members
        _memberList.postValue(memberList)
    }
}