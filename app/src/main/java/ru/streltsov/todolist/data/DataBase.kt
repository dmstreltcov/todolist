package ru.streltsov.todolist.data

import android.os.Parcelable
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import ru.streltsov.todolist.ui.tasklist.Task as TaskTD

interface DataBase {
    fun login(
        email: String,
        password: String
    ): Task<AuthResult>  //<- вот это мне не совсем нравится, какой-то костыль

    fun currentUser(): Parcelable
    fun signUp(email: String, password: String): Task<AuthResult>
    fun getData(): Query
    fun deleteTask(id:String?)
    fun addTask(task: ru.streltsov.todolist.ui.tasklist.Task)
    fun updateTask(task: ru.streltsov.todolist.ui.tasklist.Task)
    fun getTaskByID(id:String)

    fun setCallback(callback: Callback)
    interface Callback{
        fun returnInfo(message:String)
        fun returnData(task: TaskTD?)

    }
}