package com.kstudio.diarymylife.data

import android.net.Uri
import com.kstudio.diarymylife.domain.model.Event
import java.time.LocalDateTime

data class MoodRequest(
    val moodId: Long?,
    val mood: Int?,
    val title: String,
    val description: String,
    val activity: List<Event>?,
    val imageName: String?,
    val timestamp: LocalDateTime,
    val createTime: LocalDateTime,
    val uri: Uri? = null,
    val fileName: String? = null,
    val isImageUriChanged: Boolean
)
