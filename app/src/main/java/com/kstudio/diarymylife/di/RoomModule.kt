package com.kstudio.diarymylife.di

import com.kstudio.diarymylife.database.JournalDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val roomModule = module {
    single { JournalDatabase.getDatabase(androidContext()).journalDao() }
}