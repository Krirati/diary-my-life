package com.kstudio.diarymylife.di

import com.kstudio.diarymylife.data.activity_event.ActivityEventRepository
import com.kstudio.diarymylife.data.activity_event.ActivityEventRepositoryImpl
import com.kstudio.diarymylife.data.mood.MoodRepository
import com.kstudio.diarymylife.data.mood.MoodRepositoryImpl
import com.kstudio.diarymylife.data.shared_preferences.SharedPreferencesRepository
import com.kstudio.diarymylife.data.shared_preferences.SharedPreferencesRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<MoodRepository> { MoodRepositoryImpl(get(), get()) }
    single<ActivityEventRepository> { ActivityEventRepositoryImpl(get()) }
    single<SharedPreferencesRepository> { SharedPreferencesRepositoryImpl(sharedPreferences = get()) }
}