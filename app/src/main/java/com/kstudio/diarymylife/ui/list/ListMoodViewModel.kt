package com.kstudio.diarymylife.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.data.mood.MoodRepository
import com.kstudio.diarymylife.domain.GetMoodsAndActivityUseCase
import com.kstudio.diarymylife.domain.model.MoodViewType
import com.kstudio.diarymylife.ui.base.BaseViewModel
import com.kstudio.diarymylife.utils.BackgroundTheme.Companion.Poor
import com.kstudio.diarymylife.utils.BackgroundTheme.Companion.Excellent
import com.kstudio.diarymylife.utils.BackgroundTheme.Companion.Average
import com.kstudio.diarymylife.utils.BackgroundTheme.Companion.Good
import com.kstudio.diarymylife.utils.BackgroundTheme.Companion.Very_Poor
import com.kstudio.diarymylife.widgets.custom_chart.BarData
import kotlinx.coroutines.launch

class ListMoodViewModel constructor(
    private val moodRepository: MoodRepository,
    private val getMoodsAndActivityUseCase: GetMoodsAndActivityUseCase
) : BaseViewModel() {

    private val _memberList: MutableLiveData<List<MoodViewType>> = MutableLiveData()
    fun getMemberList(): LiveData<List<MoodViewType>> = _memberList

    private val _barData: MutableLiveData<Pair<Int, ArrayList<BarData>>> = MutableLiveData()
    val barData: LiveData<Pair<Int, ArrayList<BarData>>> = _barData

    private val _averageMood: MutableLiveData<Long> = MutableLiveData(0)
    val averageMood: LiveData<Long> = _averageMood

    fun fetchRecentJournal() {
        viewModelScope.launch {
            getMoodsAndActivityUseCase.invoke().collect {
                _memberList.postValue(it)
                mappingDataToChart(it)
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

    private fun mappingDataToChart(moodViewTypes: List<MoodViewType>) {
        val dataList = ArrayList<BarData>()
        var maxValue = 0
        moodViewTypes.groupBy { it.data?.mood }.forEach { (moodType, moods) ->
            maxValue = if (moods.size > maxValue) moods.size else maxValue
            val data = when (moodType) {
                Very_Poor -> BarData(5, moods.size.toFloat(), R.color.sandy_brown, R.drawable.mood1)
                Poor -> BarData(4, moods.size.toFloat(), R.color.deep_champagne, R.drawable.mood2)
                Average -> BarData(
                    3,
                    moods.size.toFloat(),
                    R.color.lemon_yellow_crayola,
                    R.drawable.mood3
                )
                Good -> BarData(2, moods.size.toFloat(), R.color.pale_sprint_bud, R.drawable.mood4)
                Excellent-> BarData(1, moods.size.toFloat(), R.color.laurel_green, R.drawable.mood5)
                else -> null
            }
            data?.let { dataList.add(it) }
        }
        dataList.sortBy { it.barOrder }

        _barData.postValue(maxValue to dataList)
    }
}