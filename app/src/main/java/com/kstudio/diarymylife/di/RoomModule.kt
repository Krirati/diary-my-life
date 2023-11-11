package com.kstudio.diarymylife.di

import com.kstudio.diarymylife.database.MoodDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val roomModule = module {
    single { MoodDatabase.getDatabase(androidContext()).moodDao() }
    single { MoodDatabase.getDatabase(androidContext()).activityEventDao() }
}
