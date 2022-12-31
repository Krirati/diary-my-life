package com.kstudio.diarymylife.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.dsl.module

private const val MOOD_SHARED_PREFERENCES = "mood_shared_preferences"

val sharedPreferencesModule = module {
    single { initSharedPreferences(get(), MOOD_SHARED_PREFERENCES) }
}

private fun initSharedPreferences(context: Context, fileName: String): SharedPreferences {
    return context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
}