package com.kstudio.diarymylife.ui.summary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.domain.GetMoodsAndActivityUseCase
import com.kstudio.diarymylife.domain.model.MoodViewType
import com.kstudio.diarymylife.ui.base.BaseViewModel
import com.kstudio.diarymylife.utils.BackgroundTheme
import com.kstudio.diarymylife.utils.BackgroundTheme.Companion.Average
import com.kstudio.diarymylife.utils.BackgroundTheme.Companion.Excellent
import com.kstudio.diarymylife.utils.BackgroundTheme.Companion.Good
import com.kstudio.diarymylife.utils.BackgroundTheme.Companion.Poor
import com.kstudio.diarymylife.utils.BackgroundTheme.Companion.Very_Poor
import com.kstudio.diarymylife.widgets.custom_chart.BarData
import kotlinx.coroutines.launch

class SummaryMoodViewModel constructor(
    private val getMoodsAndActivityUseCase: GetMoodsAndActivityUseCase
) : BaseViewModel() {

    private val _barData: MutableLiveData<Pair<Int, ArrayList<BarData>>> = MutableLiveData()
    val barData: LiveData<Pair<Int, ArrayList<BarData>>> = _barData

    private val _averageMood: MutableLiveData<String> = MutableLiveData()
    val averageMood: LiveData<String> = _averageMood

    fun fetchRecentJournal() {
        viewModelScope.launch {
            getMoodsAndActivityUseCase.invoke().collect {
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
        _averageMood.postValue(BackgroundTheme().mapMoodStringToTitle(average.toInt()))
    }

    private fun mappingDataToChart(moodViewTypes: List<MoodViewType>) {
        val dataList = ArrayList<BarData>()
        var maxValue = 0
        moodViewTypes.groupBy { it.data?.mood }.forEach { (_, moods) ->
            maxValue = if (moods.size > maxValue) moods.size else maxValue
        }
        val group = moodViewTypes.groupBy { it.data?.mood }
        val veryPoor = group[Very_Poor]?.size ?: 0
        val poor = group[Poor]?.size ?: 0
        val average = group[Average]?.size ?: 0
        val good = group[Good]?.size ?: 0
        val excellent = group[Excellent]?.size ?: 0
        val veryPoorData =
            BarData(5, veryPoor.toFloat(), R.color.mood_very_pool_drop, R.drawable.crying)
        val poorData = BarData(4, poor.toFloat(), R.color.mood_pool_drop, R.drawable.sad)
        val averageData =
            BarData(3, average.toFloat(), R.color.mood_average_drop, R.drawable.neutral)
        val goodData = BarData(2, good.toFloat(), R.color.mood_good_drop, R.drawable.smile)
        val excellentData =
            BarData(1, excellent.toFloat(), R.color.mood_excellent_drop, R.drawable.lol)
        dataList.add(veryPoorData)
        dataList.add(poorData)
        dataList.add(averageData)
        dataList.add(goodData)
        dataList.add(excellentData)

        dataList.sortBy { it.barOrder }

        _barData.postValue(maxValue to dataList)
    }
}
