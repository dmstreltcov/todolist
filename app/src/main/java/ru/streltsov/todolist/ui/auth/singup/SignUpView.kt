package ru.streltsov.todolist.ui.auth.singup

import com.google.firebase.auth.FirebaseUser
import ru.streltsov.todolist.ui.base.BaseView

interface SignUpView : BaseView {
    fun showProgress()
    fun hideProgress()
    fun updateUI()
}