package com.kstudio.diarymylife.data.profile

import java.time.LocalDate

data class Profile(
    val profileId: Long,
    val nickname: String,
    val gender: String,
    val birthDate: LocalDate?,
)
