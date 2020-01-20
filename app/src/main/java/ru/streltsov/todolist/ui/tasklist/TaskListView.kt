package ru.streltsov.todolist.ui.tasklist

import ru.streltsov.todolist.ui.base.BaseView
import ru.streltsov.todolist.data.Action

interface TaskListView : BaseView {
    fun showProgressBar()
    fun hideProgressBar()
    fun initAdapter(taskList:ArrayList<Task>)
    fun updateList(index:Int, action: Action)

    fun addTask(index: Int)
    fun updateTask(oldIndex:Int, newIndex:Int)
    fun deleteTask(index:Int)
}