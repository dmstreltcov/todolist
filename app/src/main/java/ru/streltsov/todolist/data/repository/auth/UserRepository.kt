package ru.streltsov.todolist.data.repository.auth

import ru.streltsov.todolist.data.provides.auth.UserProvider
import ru.streltsov.todolist.data.provides.auth.UserProviderImpl
import ru.streltsov.todolist.exceptions.AuthException
import ru.streltsov.todolist.ui.auth.login.LoginPresenterCallback
import ru.streltsov.todolist.ui.auth.singup.SignUpPresenterCallback
import ru.streltsov.todolist.ui.base.Callback
import javax.inject.Inject


class UserRepository @Inject constructor(private val provider: UserProviderImpl) : UserRepositoryImpl, UserRepositoryImpl.UserCallback {

  private lateinit var callback: Callback

  override fun signInByEmail(email: String, password: String, callback: LoginPresenterCallback) {
    provider.signInByEmail(email, password, this)
    this.callback = callback
  }

  override fun signUp(email: String, password: String, callback:SignUpPresenterCallback) {
    provider.signUpByEmail(email, password, this)
    this.callback = callback
  }

  override fun onSuccess() {
    callback.onSuccess()
  }

  override fun onError(exception: Exception) {
    throw AuthException(exception)
  }


}