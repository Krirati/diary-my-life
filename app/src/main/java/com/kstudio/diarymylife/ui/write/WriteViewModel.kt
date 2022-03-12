package com.kstudio.diarymylife.ui.write

import androidx.lifecycle.*
import androidx.paging.*
import com.kstudio.diarymylife.database.DateSelectionPageSource
import com.kstudio.diarymylife.model.toDateDetails
import com.kstudio.diarymylife.ui.base.BaseViewModel
import com.kstudio.diarymylife.utils.toStringFormat
import java.time.LocalDate
import java.util.*

class WriteViewModel(
) : BaseViewModel() {

    private val _localDateTimeSelect: MutableLiveData<LocalDate> = MutableLiveData(LocalDate.now())
    val localDateTimeSelect: MutableLiveData<LocalDate> = _localDateTimeSelect

    private val _selectedDate = MutableLiveData(Date().toStringFormat())

    val selectedDate: LiveData<String>
        get() = _selectedDate

    private val _resetDateList = MutableLiveData<Long>()

    val resetDateList: LiveData<Long>
        get() = _resetDateList

    fun setSelectDate(localDateTime: LocalDate) {
        _localDateTimeSelect.postValue(localDateTime)
    }

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
}