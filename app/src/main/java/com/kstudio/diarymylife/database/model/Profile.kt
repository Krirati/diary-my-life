package com.kstudio.diarymylife.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "profile")
data class Profile(
    @PrimaryKey val profileId: Long = 0,
    @ColumnInfo(name = "nickname") val nickname: String,
    @ColumnInfo(name = "gender") val gender: String?,
    @ColumnInfo(name = "birth_date") val birthDate: LocalDate?,
)
