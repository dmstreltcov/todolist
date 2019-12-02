package ru.streltsov.todolist.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ru.streltsov.todolist.MainActivity
import ru.streltsov.todolist.R

object NotificationHelper {
    fun createNotificationChannel(
        context: Context,
        showBadge: Boolean,
        title: String?,
        description: String
    ) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channelId = "${context.packageName}-todolist"
            val channel = NotificationChannel(channelId, "todolist", NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = description
            channel.setShowBadge(showBadge)

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        val channelId = "${context.packageName}-${context.getString(R.string.app_name)}"
        val notificationBuilder = NotificationCompat.Builder(context, channelId).apply {
            setSmallIcon(R.drawable.ic_notification)
            setContentTitle(title)
            priority = NotificationCompat.PRIORITY_DEFAULT
            setAutoCancel(true)

            fun createSampleDataNotification() {
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
                setContentIntent(pendingIntent)
            }
        }
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(10001, notificationBuilder.build())

    }
}