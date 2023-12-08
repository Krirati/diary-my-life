package com.kstudio.diarymylife.extensions

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

const val NOTIFICATION_ID = 1
const val CHANNEL_ID = "CHANNEL_ID_1"
const val TITLE_EXTRA = "Mood Diary"
const val CONTENT_EXTRA = "textContent"

class Notification : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val service = NotificationService(context)
        service.showNotification()
    }
}
