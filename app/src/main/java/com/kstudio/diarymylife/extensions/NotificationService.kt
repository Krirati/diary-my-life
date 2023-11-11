package com.kstudio.diarymylife.extensions

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.kstudio.diarymylife.MainActivity
import com.kstudio.diarymylife.R
import java.util.Calendar

class NotificationService(
    private val context: Context
) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun showNotification() {
        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(TITLE_EXTRA)
            .setContentText(CONTENT_EXTRA)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(activityPendingIntent)
            .build()

        notificationManager.notify(NOTIFICATION_ID, builder)
    }

    fun scheduleNotification(hour: Int, minute: Int) {
        val intent = Intent(context, Notification::class.java)
        val title = context.getString(R.string.diary_mood)
        val message = context.getString(R.string.diary_mood_desc)
        intent.putExtra(TITLE_EXTRA, title)
        intent.putExtra(CONTENT_EXTRA, message)

        val paddingIntent = PendingIntent.getBroadcast(
            context,
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            paddingIntent
        )
    }

    fun cancelScheduleNotification() {
        notificationManager.cancel(NOTIFICATION_ID)
    }
}
