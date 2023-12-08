package com.kstudio.diarymylife.data.profile

import com.kstudio.diarymylife.data.base.Response
import com.kstudio.diarymylife.data.base.Response.Failed.toResult
import com.kstudio.diarymylife.database.dao.ProfileDao
import com.kstudio.diarymylife.database.model.Profile
import kotlinx.coroutines.flow.first

interface ProfileRepository {
    suspend fun createOrUpdateProfile(profile: com.kstudio.diarymylife.data.profile.Profile): Response
    suspend fun updateProfile(profile: com.kstudio.diarymylife.data.profile.Profile)
    suspend fun getProfile(): com.kstudio.diarymylife.data.profile.Profile
}

class ProfileRepositoryImpl(
    private val profileDao: ProfileDao
) : ProfileRepository {
    override suspend fun createOrUpdateProfile(profile: com.kstudio.diarymylife.data.profile.Profile): Response {
        val newProfile = Profile(
            profileId = profile.profileId,
            nickname = profile.nickname,
            gender = profile.gender,
            birthDate = profile.birthDate
        )
        return profileDao.insertOrUpdate(newProfile).toResult()
    }

    override suspend fun updateProfile(profile: com.kstudio.diarymylife.data.profile.Profile) {
        val newProfile = Profile(
            profileId = 0L,
            nickname = profile.nickname,
            gender = profile.gender,
            birthDate = profile.birthDate
        )
        profileDao.updateProfile(newProfile)
    }

    override suspend fun getProfile(): com.kstudio.diarymylife.data.profile.Profile {
        val profile = profileDao.getFirst().first()
        return com.kstudio.diarymylife.data.profile.Profile(
            profileId = profile.profileId,
            nickname = profile.nickname,
            birthDate = profile.birthDate,
            gender = profile.gender ?: ""
        )
    }
}

