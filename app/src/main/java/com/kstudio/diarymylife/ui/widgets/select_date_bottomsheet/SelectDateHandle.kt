package com.kstudio.diarymylife.ui.widgets.select_date_bottomsheet

import com.kstudio.diarymylife.model.ResultSelectDate

interface SelectDateHandle {
    fun onClickDoneBottomSheet(date: ResultSelectDate)
    fun onCloseBottomSheet()
}