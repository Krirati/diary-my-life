package com.kstudio.diarymylife.di

import com.kstudio.diarymylife.data.shared_preferences.SharedPreferencesRepository
import com.kstudio.diarymylife.data.shared_preferences.SharedPreferencesRepositoryImpl
import com.kstudio.diarymylife.repository.ActivityEventRepository
import com.kstudio.diarymylife.repository.MoodRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { MoodRepository(get()) }
    single { ActivityEventRepository(get()) }
    single<SharedPreferencesRepository> { SharedPreferencesRepositoryImpl(sharedPreferences = get()) }
}