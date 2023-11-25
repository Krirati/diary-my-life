package com.kstudio.diarymylife.di

import com.kstudio.diarymylife.data.datastore.DataStoreRepository
import com.kstudio.diarymylife.data.datastore.DataStoreRepositoryImpl
import org.koin.dsl.module

val dataStore = module {
    single<DataStoreRepository> { DataStoreRepositoryImpl(context = get()) }
}
