package ru.skillbranch.todolist.ui.auth.login

import ru.skillbranch.todolist.base.BasePresenter
import ru.skillbranch.todolist.base.BaseView

abstract class ILoginPresenter<T> : BasePresenter<BaseView>() {
    abstract fun onLoginButton(email: String, password: String)
    abstract fun onSingupButton()
    abstract fun onRestorePassword()
}