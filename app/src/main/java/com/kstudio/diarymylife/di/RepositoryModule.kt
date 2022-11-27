package com.kstudio.diarymylife.di

import com.kstudio.diarymylife.repository.ActivityEventRepository
import com.kstudio.diarymylife.repository.MoodRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { MoodRepository(get()) }
    single { ActivityEventRepository(get()) }
}