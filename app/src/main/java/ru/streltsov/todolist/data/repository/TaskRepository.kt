package ru.streltsov.todolist.data.repository

import ru.streltsov.todolist.ui.tasklist.Task

interface TaskRepository : BaseRepository{
    fun addTask(task: Task)
    fun updateTask()
    fun deleteTask(id:String)
    fun getTaskById(id:String)


    interface TaskCallback : Callback{
        fun sendInfo()
        fun returnTask(task: Task)
    }
}