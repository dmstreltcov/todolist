package ru.streltsov.todolist.data.repository

import ru.streltsov.todolist.ui.tasklist.Task

interface TaskRepositoryImpl{
    fun addTask(task: Task, _callback:TaskCallback)
    fun deleteTask(id:String, _callback:TaskCallback)
    fun getTaskById(id:String, _callback:TaskCallback)


    interface TaskCallback : Callback{
        fun sendInfo()
        fun returnTask(task: Task)
    }
}