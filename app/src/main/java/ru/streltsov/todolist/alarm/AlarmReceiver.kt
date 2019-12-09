package ru.streltsov.todolist.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import ru.streltsov.todolist.ui.tasklist.Task

class AlarmReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {

        val title = intent.getStringExtra("title")
        val id = intent.getStringExtra("id")
        Log.d("Alarm Reciever", "onReceive() $title  $id")
        NotificationHelper.createNotificationChannel(
            context,
            false,
            title,
            id,
            "App notification channel."
        )
    }

}