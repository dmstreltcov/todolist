package ru.streltsov.todolist.data.repository

import ru.streltsov.todolist.ui.tasklist.Task

interface TaskListRepository : BaseRepository{
    fun getAllTasks()
    fun getTasksByDay()
    fun changeStatus(id:String, status:Boolean)

    interface TaskListCallback : Callback{
        fun returnTaskList(data: ArrayList<Task>)
        fun sendMessage(message:String)
        fun updateUI(index:Int)
    }
}