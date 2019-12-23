package ru.streltsov.todolist.ui.tasklist

import ru.streltsov.todolist.base.BaseView
import ru.streltsov.todolist.data.Action

interface TaskListView : BaseView {
    fun showProgressBar()
    fun hideProgressBar()
    fun initAdapter(taskList:ArrayList<Task>)
    fun updateList(index:Int, action: Action)
}