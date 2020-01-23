package ru.streltsov.todolist.ui.auth.login

import ru.streltsov.todolist.ui.base.BaseView

interface LoginView : BaseView {
    fun signUp()
    fun showProgress()
    fun hideProgress()
    fun updateUI(uid:String)
}