package ru.skillbranch.todolist.data

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface DataBase {
    fun login(email: String, password: String): Task<AuthResult>
}