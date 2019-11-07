package ru.streltsov.todolist.data

import android.os.Parcelable
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.Query

interface DataBase {
    fun login(email: String, password: String): Task<AuthResult>  //<- вот это мне не совсем нравится, какой-то костыль
    fun currentUser():Parcelable
    fun signUp(email: String, password: String): Task<AuthResult>
    fun getData() : Query
}