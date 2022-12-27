package com.kstudio.diarymylife.data

import android.os.Parcelable
import com.kstudio.diarymylife.ui.base.swipe_event.SwipeState
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class MoodItem(
    var viewType: Int,
    var data: MoodUI?
): Parcelable

@Parcelize
data class MoodUI(
    val moodId: Long,
    val title: String,
    val desc: String,
    val mood: Int?,
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