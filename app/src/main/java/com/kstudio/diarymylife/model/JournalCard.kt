package com.kstudio.diarymylife.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
data class JournalCard(
    val journalId: Int,
    val title: String,
    val desc: String,
    val mood: String,
    val activity: ArrayList<String?>?,
    val timestamp: Date,
    val imageId: String
): Parcelable
