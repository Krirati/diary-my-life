package com.kstudio.diarymylife.data.profile

import java.time.LocalDate

data class ProfileRequest(
    val nickname: String,
    val gender: String,
    val birthDate: LocalDate?,
)
