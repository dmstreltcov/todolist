package ru.streltsov.todolist.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import ru.streltsov.todolist.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title")
        Log.d("Alarm Reciever", "onReceive()")
        NotificationHelper.createNotificationChannel(
            context,
            false,
            title,
            "App notification channel."
        )
    }
}