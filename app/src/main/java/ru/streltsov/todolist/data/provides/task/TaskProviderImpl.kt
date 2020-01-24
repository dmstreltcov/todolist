package ru.streltsov.todolist.data.provides.task

import ru.streltsov.todolist.data.repository.task.TaskRepositoryImpl
import ru.streltsov.todolist.ui.tasklist.Task

interface TaskProviderImpl {
  fun addTask(task: Task, callback: TaskRepositoryImpl.TaskCallback)
  fun deleteTask(id:String, callback: TaskRepositoryImpl.TaskCallback)
  fun updateTask(id:String, data:HashMap<String, String>, callback: TaskRepositoryImpl.TaskCallback)

}