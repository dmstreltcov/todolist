package ru.streltsov.todolist.data.repository

import ru.streltsov.todolist.data.Action
import ru.streltsov.todolist.ui.tasklist.Task

interface TaskListRepositoryImpl {
    fun getAllTasks(_callback: TaskListCallback)
    fun getTasksByDay()
    fun changeStatus(id: String, status: Boolean, _callback: TaskListCallback)

    interface TaskListCallback {
        fun returnTaskList(data: ArrayList<Task>)
        fun sendMessage(message: String)
        fun updateList(index: Int, action: Action)

        fun addTask(index: Int)
        fun updateTask(oldIndex: Int, newIndex: Int)
        fun deleteTask(index: Int)
    }
}