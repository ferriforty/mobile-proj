package com.example.mobile_proj.ui.screens.schedule

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Color
import androidx.core.app.NotificationCompat
import com.example.mobile_proj.R
import java.time.LocalDate
import java.util.Calendar


class ScheduleViewModel(val context: Context) {

    private val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

    private val channelId = "my_channel_id"
    private val importance = NotificationManager.IMPORTANCE_HIGH
    private val notificationChannel = NotificationChannel(channelId, "zosky", importance)

    init {
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationManager.createNotificationChannel(notificationChannel)
    }

    fun schedulePushNotifications(dayName: Array<String>) {

        val notificationIntent = Intent(context, NotificationReceiver::class.java)
        notificationIntent.putExtra("notificationId", 1)
        notificationIntent.putExtra("title", "GymShred")
        notificationIntent.putExtra("message", "It's workout day king!")
        notificationIntent.putExtra("days", dayName)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        val interval = 24 * 60 * 60 * 1000 // interval in milliseconds

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 9) // 12pm
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        alarmManager.cancel(pendingIntent)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            interval.toLong(),
            pendingIntent
        )
    }
}
class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        val notificationId = intent.getIntExtra("notificationId", 0)
        val message = intent.getStringExtra("message")
        val title = intent.getStringExtra("title")
        val days = intent.getStringArrayExtra("days")

        val today = LocalDate.now().dayOfWeek.name

        if (days.orEmpty().map { x -> x.uppercase() }.none { x -> x == today }) {
            return
        }

        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder = NotificationCompat.Builder(context, "my_channel_id")
            .setContentTitle(title)
            .setSmallIcon(R.drawable.ic_dark_theme)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}
