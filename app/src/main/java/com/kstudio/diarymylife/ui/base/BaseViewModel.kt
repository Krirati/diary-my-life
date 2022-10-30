package com.kstudio.diarymylife.ui.base

import androidx.lifecycle.*
import androidx.paging.*
import com.kstudio.diarymylife.data.ActivityDetail
import com.kstudio.diarymylife.data.toDateDetails
import com.kstudio.diarymylife.database.DateSelectionPageSource
import com.kstudio.diarymylife.entity.ActivityEvent
import com.kstudio.diarymylife.utils.toStringFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

open class BaseViewModel : ViewModel() {

    private val _localDateSelect: MutableLiveData<LocalDate> = MutableLiveData(LocalDate.now())
    val localDateSelect: MutableLiveData<LocalDate> = _localDateSelect

    private val _localTimeSelect: MutableLiveData<LocalTime> = MutableLiveData(LocalTime.now())
    val localTimeSelect: MutableLiveData<LocalTime> = _localTimeSelect

    private val _selectedDate = MutableLiveData(Date().toStringFormat())
    val selectedDate: LiveData<String> = _selectedDate

    private val _resetDateList = MutableLiveData<Long?>()
    val resetDateList: LiveData<Long?> get() = _resetDateList

    private val _currentScreen = MutableLiveData<Int>()
    val currentScreen: LiveData<Int> = _currentScreen

    private val dateSelectionPager: LiveData<PagingData<Date>> =
        Transformations.switchMap(selectedDate) { changedSelectedDate ->
            Pager(PagingConfig(pageSize = 10)) {
                DateSelectionPageSource(changedSelectedDate)
            }.liveData.cachedIn(viewModelScope)
        }

    val dateDetailsList = dateSelectionPager.map { pagingData ->
        pagingData.map { mealDate ->
            mealDate.toDateDetails()
        }
    }

    fun selectCurrentPage(page: Int) {
        _currentScreen.postValue(page)
    }

    @JvmOverloads
    fun updateCurrentSelectedDate(date: String, invalidateList: Boolean = false) {
        if (invalidateList) {
            _resetDateList.value = System.currentTimeMillis()
        }
        _selectedDate.value = date
    }

    fun setSelectDate(localDateTime: LocalDate) {
        _localDateSelect.postValue(localDateTime)
    }

    fun setSelectTime(localTime: LocalTime) {
        _localTimeSelect.postValue(localTime)
    }

    fun setResetDate(reset: Long?) {
        _resetDateList.postValue(reset)
    }

    fun List<ActivityEvent>.asActivityDetail() = map {
        ActivityDetail(
            eventId = it.eventId,
            activityName = it.activityName,
            activityImage = it.activityImage
        )
    }
}