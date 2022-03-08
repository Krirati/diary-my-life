package com.kstudio.diarymylife.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import kotlin.collections.ArrayList

@Entity(tableName = "journal_table")
data class Journal(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "mood") val mood: String?,
    @ColumnInfo(name = "activity") val activity: ArrayList<String>? = arrayListOf(),
    @ColumnInfo(name = "image_name") val imageName: String?,
    @ColumnInfo(name = "timestamp") val timestamp: LocalDateTime,
    @ColumnInfo(name = "create_time") val createTime: LocalDateTime,
) {

}