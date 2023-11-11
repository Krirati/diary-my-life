package com.kstudio.diarymylife.widgets.select_date_bottomsheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import androidx.paging.map
import com.kstudio.diarymylife.data.toDateDetails
import com.kstudio.diarymylife.database.DateSelectionPageSource
import com.kstudio.diarymylife.ui.base.BaseViewModel
import com.kstudio.diarymylife.utils.toStringFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.Date

class SelectDateBottomSheetViewModel : BaseViewModel() {
    private val _localDateSelect: MutableLiveData<LocalDate> = MutableLiveData(LocalDate.now())
    val localDateSelect: MutableLiveData<LocalDate> = _localDateSelect

    private val _localTimeSelect: MutableLiveData<LocalTime> = MutableLiveData(LocalTime.now())
    val localTimeSelect: MutableLiveData<LocalTime> = _localTimeSelect

    private val _selectedDate = MutableLiveData(Date().toStringFormat())
    val selectedDate: LiveData<String> = _selectedDate

    private val _resetDateList = MutableLiveData<Long?>()
    val resetDateList: LiveData<Long?> get() = _resetDateList

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

    fun setSelectTime(localTime: LocalTime) {
        _localTimeSelect.postValue(localTime)
    }

    fun setResetDate(reset: Long?) {
        _resetDateList.postValue(reset)
    }

    fun setSelectDate(localDateTime: LocalDateTime?) {
        if (localDateTime == null) return
        val date: Date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())

        _selectedDate.value = date.toStringFormat()
        _localTimeSelect.value = localDateTime.toLocalTime()
    }
}
