package ru.streltsov.todolist.data.repository

import com.google.firebase.auth.FirebaseUser
import ru.streltsov.todolist.data.provides.UserProviderImpl
import ru.streltsov.todolist.exceptions.AuthException
import javax.inject.Inject


class UserRepository @Inject constructor(private val provider: UserProviderImpl) : UserRepositoryImpl, UserRepositoryImpl.UserCallback {
  lateinit var uid: String

  override fun signInByEmail(email: String, password: String) {
    provider.signInByEmail(email, password, this)
  }

  override fun signUp(email: String, password: String) {
    provider.signUpByEmail(email, password, this)
  }

  override fun getUserId() = uid

  override fun onSuccess(user: FirebaseUser) {
    uid = user.uid
  }

  override fun onError(exception: Exception) {
    throw AuthException(exception)
  }


}