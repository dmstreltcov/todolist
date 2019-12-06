package ru.streltsov.todolist.ui.auth.singup

import com.google.firebase.auth.FirebaseUser
import ru.streltsov.todolist.base.BaseView

interface SignUpView : BaseView {
    fun updateUI(user: FirebaseUser)
    fun showProgress()
    fun hideProgress()
}