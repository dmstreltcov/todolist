package ru.streltsov.todolist.data.repository

import ru.streltsov.todolist.data.Action
import ru.streltsov.todolist.data.provides.TaskListProvider
import ru.streltsov.todolist.ui.tasklist.Task
import javax.inject.Inject

class TaskListRepository @Inject constructor(private val provider: TaskListProvider) : TaskListRepositoryImpl, TaskListRepositoryImpl.TaskListCallback {

  override fun getAllTasks(_callback: TaskListRepositoryImpl.TaskListCallback) {

  }

  override fun updateTask(oldIndex: Int, newIndex: Int) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun deleteTask(index: Int) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun returnTaskList(data: ArrayList<Task>) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun sendMessage(message: String) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun updateList(index: Int, action: Action) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun addTask(index: Int) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getTasksByDay() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun changeStatus(id: String, status: Boolean, _callback: TaskListRepositoryImpl.TaskListCallback) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}