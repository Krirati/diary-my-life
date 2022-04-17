package com.kstudio.diarymylife.di

import com.kstudio.diarymylife.repository.ActivityEventRepository
import com.kstudio.diarymylife.repository.JournalRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { JournalRepository(get()) }
    single { ActivityEventRepository(get()) }
}