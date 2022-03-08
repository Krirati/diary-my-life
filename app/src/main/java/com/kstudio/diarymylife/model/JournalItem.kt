package com.kstudio.diarymylife.model

import android.os.Parcelable
import com.kstudio.diarymylife.ui.base.SwipeEvent.SwipeState
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
data class JournalItem(
    var viewType: Int,
    var data: JournalUI?
): Parcelable

@Parcelize
data class JournalUI(
    val journalId: Long,
    val title: String,
    val desc: String,
    val mood: String,
    val activity: ArrayList<String>?,
    val timestamp: LocalDateTime,
    val imageId: String,
    var state : SwipeState = SwipeState.NONE,
): Parcelable