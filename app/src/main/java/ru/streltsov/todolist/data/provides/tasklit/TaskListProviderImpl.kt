package ru.streltsov.todolist.data.provides.tasklit

import ru.streltsov.todolist.data.repository.tasklist.TaskListRepositoryImpl
import java.util.*

interface TaskListProviderImpl {
  fun getAllTasks(callback: TaskListRepositoryImpl.Callback)
  fun getTaskById(id: String, callback: TaskListRepositoryImpl.Callback)
  fun getTasksByDay(day: Date, callback: TaskListRepositoryImpl.Callback)
  fun changeTaskStatus(id: String, status: Boolean, callback: TaskListRepositoryImpl.Callback)

}