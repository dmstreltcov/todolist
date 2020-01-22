package ru.streltsov.todolist.ui.utils

import com.google.firebase.Timestamp
import ru.streltsov.todolist.ui.tasklist.Header
import ru.streltsov.todolist.ui.tasklist.Item
import ru.streltsov.todolist.ui.tasklist.Task
import java.util.*
import kotlin.collections.ArrayList

class TaskListUtils {
    fun createNewTaskList(list: List<Task>) : ArrayList<Item> {
        val newList:ArrayList<Item> = ArrayList()
        val prev = Header("Просроченный")
        val today = Header("Сегодня")
        for (task in list) {
            val day: Item = setHeaderDay(Timestamp(task.dateStart!!.seconds, task.dateStart.nanoseconds))
            when {
                isExpired(newList, prev, task.dateStart) -> {
                    newList.add(task)
                }
                task.dateStart.seconds * 1000 < System.currentTimeMillis() -> {
                    newList.add(prev)
                    newList.add(task)
                }
                isNewDayTask(newList, day, task.dateStart) -> {
                    newList.add(task)
                }
                else -> {
                    newList.add(day)
                    newList.add(task)
                }
            }
        }
        return newList
    }

    private fun isNewDayTask(
        newList:ArrayList<Item>,
        day: Item,
        dateStart: Timestamp
    ): Boolean {
       return (dateStart.seconds * 1000 > System.currentTimeMillis()) and newList.contains(day)
    }

    fun setHeaderDay(timestamp: Timestamp): Item {
        val calendar = Calendar.getInstance()
        calendar.time = timestamp.toDate()
        val month: String = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())!!
        val day: String = calendar.get(Calendar.DATE).toString()
        return Header("$day $month")
    }

    private fun isExpired(newList:ArrayList<Item>, prev: Header, dateStart: Timestamp):Boolean{
       return newList.contains(prev) and (dateStart.seconds * 1000 < System.currentTimeMillis())
    }


}