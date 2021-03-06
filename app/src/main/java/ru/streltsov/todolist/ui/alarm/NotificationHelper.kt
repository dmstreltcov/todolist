package ru.streltsov.todolist.ui.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ru.streltsov.todolist.R
import ru.streltsov.todolist.ui.task.TaskActivity
import ru.streltsov.todolist.ui.task.TaskType

object NotificationHelper {
    fun createNotificationChannel(
        context: Context,
        showBadge: Boolean,
        title: String?,
        id:String?,
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

        val intent = Intent(context, TaskActivity::class.java)
        intent.putExtra("taskID", id)
        intent.putExtra("flag", TaskType.EDIT)
        val pendingIntent = PendingIntent.getActivity(context, id.hashCode(), intent, PendingIntent.FLAG_ONE_SHOT)

        val channelId = "${context.packageName}-${context.getString(R.string.app_name)}"
        val notificationBuilder = NotificationCompat.Builder(context, channelId).apply {
            setSmallIcon(R.drawable.ic_isolated_monochrome_black)
            setContentTitle(title)
            priority = NotificationCompat.PRIORITY_DEFAULT
            setAutoCancel(true)
            setContentIntent(pendingIntent)
            setStyle(NotificationCompat.InboxStyle())
            setGroupSummary(true)
        }

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
    }
}

//TODO уведомления оставляю так. Возможно будет рефакторинг...
// Здесь сейчас итак не все гладко