package ru.streltsov.todolist.ui.tasklist

import ru.streltsov.todolist.base.BaseView

interface TaskListView : BaseView {
    fun showProgressBar()
    fun hideProgressBar()
    fun initAdapter(taskList:ArrayList<Task>)
    fun updateUI(index:Int)
}