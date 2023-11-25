package com.kstudio.diarymylife.domain

import com.kstudio.diarymylife.data.base.Response
import com.kstudio.diarymylife.data.profile.ProfileRepository
import com.kstudio.diarymylife.data.profile.ProfileRequest

class ProfileUseCase constructor(private val profileRepository: ProfileRepository) {
    suspend fun createProfile(profile: ProfileRequest): Response {
        return profileRepository.createProfile(profile)
    }
}