package ru.streltsov.todolist.data.repository

import ru.streltsov.todolist.ui.tasklist.Task

interface TaskListRepository : BaseRepository{
    fun getAllTasks()
    fun getTasksByDay()
    fun getTaskById(id:String)
    fun changeStatus(id:String, status:Boolean)

    interface TaskListCallback : Callback{
        fun returnTask(task: Task)
        fun returnTaskList(data: ArrayList<Task>)
        fun sendMessage(message:String)
    }
}