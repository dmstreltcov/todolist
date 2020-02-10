package ru.streltsov.todolist.data.provides.impl

import ru.streltsov.todolist.ui.base.Callback

interface TaskListProvider {
  fun getAllTasks(callback: Callback.TaskListCallback)
  fun getTaskById()
  fun getTasksByDay()
  fun changeTaskStatus()

}