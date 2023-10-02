package com.kstudio.diarymylife.ui.summary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.data.mood.MoodRepository
import com.kstudio.diarymylife.domain.GetMoodsAndActivityUseCase
import com.kstudio.diarymylife.domain.model.MoodViewType
import com.kstudio.diarymylife.ui.base.BaseViewModel
import com.kstudio.diarymylife.utils.BackgroundTheme
import com.kstudio.diarymylife.utils.BackgroundTheme.Companion.Poor
import com.kstudio.diarymylife.utils.BackgroundTheme.Companion.Excellent
import com.kstudio.diarymylife.utils.BackgroundTheme.Companion.Average
import com.kstudio.diarymylife.utils.BackgroundTheme.Companion.Good
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
        moodViewTypes.groupBy { it.data?.mood }.forEach { (moodType, moods) ->
            maxValue = if (moods.size > maxValue) moods.size else maxValue
            val data = when (moodType) {
                Very_Poor -> BarData(5, moods.size.toFloat(), R.color.mood_very_pool_drop, R.drawable.crying)
                Poor -> BarData(4, moods.size.toFloat(), R.color.mood_pool_drop, R.drawable.sad)
                Average -> BarData(
                    3,
                    moods.size.toFloat(),
                    R.color.mood_average_drop,
                    R.drawable.neutral
                )
                Good -> BarData(2, moods.size.toFloat(), R.color.mood_good_drop, R.drawable.smile)
                Excellent-> BarData(1, moods.size.toFloat(), R.color.mood_excellent_drop, R.drawable.lol)
                else -> null
            }
            data?.let { dataList.add(it) }
        }
        dataList.sortBy { it.barOrder }

        _barData.postValue(maxValue to dataList)
    }
}