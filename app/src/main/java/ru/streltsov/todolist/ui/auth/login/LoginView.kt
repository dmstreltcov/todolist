package ru.streltsov.todolist.ui.auth.login

import com.google.firebase.auth.FirebaseUser
import ru.streltsov.todolist.base.BaseView

interface LoginView : BaseView{
    fun updateUI(user: FirebaseUser)
    fun signUp()
}