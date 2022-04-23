package com.kstudio.diarymylife.widgets.select_date_bottomsheet

import com.kstudio.diarymylife.data.ResultSelectDate

interface SelectDateHandle {
    fun onClickDoneBottomSheet(date: ResultSelectDate)
    fun onCloseBottomSheet()
}