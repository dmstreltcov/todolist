package ru.skillbranch.todolist.ui.auth.login

import com.google.firebase.auth.FirebaseUser
import ru.skillbranch.todolist.base.BaseView

interface LoginView : BaseView{
    fun updateUI(user: FirebaseUser)
    fun signUp()
}