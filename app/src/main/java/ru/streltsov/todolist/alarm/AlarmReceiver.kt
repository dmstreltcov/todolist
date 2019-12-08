package ru.streltsov.todolist.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import ru.streltsov.todolist.ui.tasklist.Task

class AlarmReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {

        val task = getTask(intent)
        Log.d("Alarm Reciever", "onReceive() $task")
        NotificationHelper.createNotificationChannel(
            context,
            false,
            task,
            "App notification channel."
        )
    }

    fun getTask(intent: Intent): Task? {
        return intent.extras?.getParcelable("task")
    }

}