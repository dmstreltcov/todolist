package ru.streltsov.todolist.data.repository.task

import ru.streltsov.todolist.ui.tasklist.Task

interface TaskRepositoryImpl{
    fun addTask(task: Task, callback: TaskCallback)
    fun deleteTask(id:String, callback: TaskCallback)
    fun getTaskById(id:String, callback: TaskCallback)


    interface TaskCallback{
        fun sendInfo()
        fun returnTask(task: Task)
    }
}