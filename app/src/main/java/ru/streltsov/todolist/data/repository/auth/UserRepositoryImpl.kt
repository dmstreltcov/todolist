package ru.streltsov.todolist.data.repository.auth

import com.google.firebase.auth.FirebaseUser
import ru.streltsov.todolist.ui.auth.login.LoginPresenterCallback
import java.lang.Exception

interface UserRepositoryImpl {
  fun signInByEmail(email: String, password: String, callback: LoginPresenterCallback)
  fun signUp(email: String, password: String)
//  fun getUserId(): String

  interface UserCallback{
    fun onSuccess(user: FirebaseUser?)
    fun onError(exception: Exception)
  }

}