package ru.streltsov.todolist.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log

object Utils {

    fun setAlarm(context: Context, timeOfAlarm: Long) {

        Log.d("Alarm log", "time is = $timeOfAlarm")
        val broadcastIntent = Intent(context, NotificationReceiver::class.java)
        val pIntent = PendingIntent.getBroadcast(
            context,
            0,
            broadcastIntent,
            0
        )

        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if(System.currentTimeMillis() < timeOfAlarm){
//            alarmMgr.setAlarmClock(
//                AlarmManager.AlarmClockInfo(timeOfAlarm, pIntent),pIntent
//            )
            alarmMgr.set(
                AlarmManager.RTC_WAKEUP,
                timeOfAlarm,
                pIntent
            )
            Log.d("Alarm is set", "All be ok")
        }
    }
}
