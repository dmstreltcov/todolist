package ru.streltsov.todolist.ui.tasklist

import ru.streltsov.todolist.base.BaseView

interface TaskListView : BaseView {
    fun showProgressBar()
    fun hideProgressBar()
}