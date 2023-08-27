package com.kstudio.diarymylife.data

import android.net.Uri
import java.time.LocalDateTime

data class MoodRequest(
    val moodId: Long?,
    val mood: Int?,
    val description: String,
    val activity: ArrayList<ActivityDetail>? = arrayListOf(),
    val imageName: String?,
    val timestamp: LocalDateTime,
    val createTime: LocalDateTime,
    val uri: Uri? = null,
    val fileName: String? = null
)
