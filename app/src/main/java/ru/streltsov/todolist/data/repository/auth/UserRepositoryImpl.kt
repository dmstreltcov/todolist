package ru.streltsov.todolist.data.repository.auth

import ru.streltsov.todolist.ui.auth.login.LoginPresenterCallback
import ru.streltsov.todolist.ui.auth.singup.SignUpPresenterCallback
import java.lang.Exception

interface UserRepositoryImpl {
  fun signInByEmail(email: String, password: String, callback: LoginPresenterCallback)
  fun signUp(email: String, password: String, callback: SignUpPresenterCallback)

  interface UserCallback{
    fun onSuccess()
    fun onError(exception: Exception)
  }

}