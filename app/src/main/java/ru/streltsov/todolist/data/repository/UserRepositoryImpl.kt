package ru.streltsov.todolist.data.repository

import com.google.firebase.auth.FirebaseUser
import java.lang.Exception

interface UserRepositoryImpl {
  fun signInByEmail(email: String, password: String)
  fun signUp(email: String, password: String)
  fun getUserId(): String

  interface UserCallback{
    fun onSuccess(user:FirebaseUser)
    fun onError(exception: Exception)
  }

}