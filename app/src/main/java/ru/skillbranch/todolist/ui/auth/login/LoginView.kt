package ru.skillbranch.todolist.ui.auth.login

import ru.skillbranch.todolist.base.BaseView

interface LoginView : BaseView{
    fun login()
    fun updateUI()
}