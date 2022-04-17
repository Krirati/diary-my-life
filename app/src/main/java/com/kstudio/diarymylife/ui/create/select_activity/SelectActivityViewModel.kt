package com.kstudio.diarymylife.ui.create.select_activity

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.data.ActivityEvent
import com.kstudio.diarymylife.model.ActivityDetail
import com.kstudio.diarymylife.repository.ActivityEventRepository
import com.kstudio.diarymylife.ui.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


class SelectActivityViewModel(
    private val activityEventRepository: ActivityEventRepository
) : BaseViewModel() {
    private var _listActivity: MutableLiveData<List<ActivityDetail>> = MutableLiveData()
    var listActivity = _listActivity


    fun updateListActivity() {
        viewModelScope.launch(Dispatchers.IO) {
            activityEventRepository.getAll()
                .onStart { }
                .onCompletion { }
                .catch { }
                .collect {
                    _listActivity.postValue(it.asActivityDetail())
                }
        }
    }


}