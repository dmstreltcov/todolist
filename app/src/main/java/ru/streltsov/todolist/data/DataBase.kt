package ru.streltsov.todolist.data

import android.os.Parcelable
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface DataBase {
    fun login(email: String, password: String): Task<AuthResult>  //<- вот это мне не совсем нравится, какой-то костыль
    fun currentUser():Parcelable
}