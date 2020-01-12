package ru.streltsov.todolist.data.repository

import android.os.Parcelable
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface UserRepository{
    fun login(email: String,password: String, _callback: UserCallback)  //<- вот это мне не совсем нравится, какой-то костыль
    fun signUp(email: String, password: String, _callback: UserCallback)

    interface UserCallback : Callback{

    }

    //TODO добавить осмысленный колбэк на возвращение результата
    /*
    Получить id пользователя
    Авторизоваться
    Зарегистрироваться
     */

}