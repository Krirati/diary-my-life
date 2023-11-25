package com.kstudio.diarymylife.data.profile

import com.kstudio.diarymylife.data.base.Response.Failed.toResult
import com.kstudio.diarymylife.database.dao.ProfileDao
import com.kstudio.diarymylife.database.model.Profile
import com.kstudio.diarymylife.data.base.Response

interface ProfileRepository {
    suspend fun createProfile(profile: ProfileRequest): Response
}

class ProfileRepositoryImpl(
    private val profileDao: ProfileDao
) : ProfileRepository {
    override suspend fun createProfile(profile: ProfileRequest): Response {
        val newProfile = Profile(
            Math.random().toLong(),
            profile.nickname,
            profile.gender,
            profile.birthDate
        )
        return profileDao.insert(newProfile).toResult()
    }
}

