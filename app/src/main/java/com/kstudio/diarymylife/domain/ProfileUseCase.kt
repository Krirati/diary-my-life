package com.kstudio.diarymylife.domain

import com.kstudio.diarymylife.data.base.Response
import com.kstudio.diarymylife.data.profile.ProfileRepository
import com.kstudio.diarymylife.data.profile.Profile

class ProfileUseCase constructor(private val profileRepository: ProfileRepository) {
    suspend fun createProfile(profile: Profile): Response {
        return profileRepository.createOrUpdateProfile(profile)
    }

    suspend fun getProfile(): Profile {
        return profileRepository.getProfile()
    }

    suspend fun updateProfile(profile: Profile): Response {
        profileRepository.updateProfile(profile)
        return Response.Success(1)
    }
}