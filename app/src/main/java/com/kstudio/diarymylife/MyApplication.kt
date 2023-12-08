package com.kstudio.diarymylife

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.kstudio.diarymylife.di.dataStore
import com.kstudio.diarymylife.di.repositoryModule
import com.kstudio.diarymylife.di.roomModule
import com.kstudio.diarymylife.di.sharedPreferencesModule
import com.kstudio.diarymylife.di.useCaseModule
import com.kstudio.diarymylife.di.viewModelModule
import com.kstudio.diarymylife.extensions.CHANNEL_ID
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        setUpKoin()
    }

    private fun setUpKoin() {
        startKoin {
            androidContext(this@MyApplication)
            modules(
                viewModelModule,
                roomModule,
                repositoryModule,
                useCaseModule,
                sharedPreferencesModule,
                dataStore
            )
        }
    }

    private fun createNotificationChannel() {
        val name = "Mood Diary"
        val desc = "A description of channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = desc
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
