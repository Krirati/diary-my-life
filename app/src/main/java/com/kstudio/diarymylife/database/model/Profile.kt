package com.kstudio.diarymylife.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "profile")
data class Profile(
    @PrimaryKey(autoGenerate = true) val profileId: Long = 0,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "nickname") val nickname: String,
    @ColumnInfo(name = "gender") val gender: String,
    @ColumnInfo(name = "birth_date") val birthDate: LocalDateTime,
)
