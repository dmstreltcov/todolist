package ru.streltsov.todolist.ui.tasklist

interface TaskListPresenterImpl {
  interface Callback{
    fun setTaskList(list:ArrayList<Task>)
  }
}