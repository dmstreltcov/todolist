package ru.streltsov.todolist.ui.auth.singup

import ru.streltsov.todolist.ui.base.BaseView

interface SignUpView : BaseView {
    fun showProgress()
    fun hideProgress()
    fun updateUI()
}