package ru.streltsov.todolist.ui.base

import ru.streltsov.todolist.ui.tasklist.Task
import java.lang.Exception

interface Callback {
    fun onSuccess()
    fun onError(exception: Exception)

    interface TaskListCallback : Callback{
        fun returnList(list:ArrayList<Task>)
    }

    interface TaskCallback :Callback{
        fun returnTask(task:Task)
    }
}

