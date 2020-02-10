package ru.streltsov.todolist.data.provides.impl

import ru.streltsov.todolist.ui.tasklist.Task
import ru.streltsov.todolist.ui.base.Callback

interface TaskProvider {
  fun addTask(task: Task, callback:Callback)
  fun deleteTask(id:String)
  fun updateTask(id:String)
  fun getTaskById(id:String, callback: Callback.TaskCallback)
}