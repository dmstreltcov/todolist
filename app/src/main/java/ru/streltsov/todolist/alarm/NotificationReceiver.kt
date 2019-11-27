package ru.streltsov.todolist.alarm

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import ru.streltsov.todolist.R


class NotificationReceiver : BroadcastReceiver() {
    private lateinit var pIntent:PendingIntent

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("WHAT IS IT", "$context")
        // Create the notification to be shown
        val intent: Intent = Intent(context, NotificationReceiver::class.java)
        pIntent = PendingIntent.getBroadcast(context,0, intent, 0)

        val builder =
            NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Title")
                .setContentText("Notification text")
                .setContentIntent(pIntent)

        val notification: Notification = builder.build()

        val notificationManager = context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)





//        val mBuilder = NotificationCompat.Builder(context!!)
//            .setSmallIcon(R.mipmap.ic_launcher)
//            .setContentTitle("Alarm")
//            .setContentText("Here\'s your alarm!")
//            .setPriority(NotificationCompat.PRIORITY_MAX)
//            .build()
//
//        // Get the Notification manager service
//        val id = System.currentTimeMillis() / 1000
//        val am = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        am.notify(1,mBuilder)
//        // Generate an Id for each notification

        // Show a notification
    }
}