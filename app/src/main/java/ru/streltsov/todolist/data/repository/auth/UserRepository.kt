package ru.streltsov.todolist.data.repository.auth

import com.google.firebase.auth.FirebaseUser
import ru.streltsov.todolist.data.provides.auth.UserProviderImpl
import ru.streltsov.todolist.exceptions.AuthException
import ru.streltsov.todolist.ui.auth.login.LoginPresenterCallback
import javax.inject.Inject


class UserRepository @Inject constructor(private val provider: UserProviderImpl) : UserRepositoryImpl, UserRepositoryImpl.UserCallback {

  private lateinit var callback: LoginPresenterCallback
  override fun signInByEmail(email: String, password: String, callback: LoginPresenterCallback) {
    provider.signInByEmail(email, password, this)
    this.callback = callback
  }

  override fun signUp(email: String, password: String) {
    provider.signUpByEmail(email, password, this)
  }

  override fun onSuccess(user: FirebaseUser?) {
    callback.onSuccess()
  }

  override fun onError(exception: Exception) {
    throw AuthException(exception)
  }


}