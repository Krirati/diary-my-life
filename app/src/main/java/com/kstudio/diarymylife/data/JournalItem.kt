package com.kstudio.diarymylife.data

import android.os.Parcelable
import com.kstudio.diarymylife.ui.base.SwipeEvent.SwipeState
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

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
    val activity: List<ActivityDetail>?,
    val timestamp: LocalDateTime,
    val imageId: String,
    var state : SwipeState = SwipeState.NONE,
): Parcelable

@Parcelize
data class ActivityDetail(
    val eventId: Int,
    val activityName: String,
    val activityImage: String,
): Parcelable