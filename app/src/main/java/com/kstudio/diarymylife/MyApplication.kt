package com.kstudio.diarymylife

import android.app.Application
import com.kstudio.diarymylife.di.repositoryModule
import com.kstudio.diarymylife.di.roomModule
import com.kstudio.diarymylife.di.sharedPreferencesModule
import com.kstudio.diarymylife.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setUpKoin()
    }

    private fun setUpKoin() {
        startKoin {
            androidContext(this@MyApplication)
            modules(
                viewModelModule,
                roomModule,
                repositoryModule,
                sharedPreferencesModule
            )
        }
    }
}