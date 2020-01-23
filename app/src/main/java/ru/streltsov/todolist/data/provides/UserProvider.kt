package ru.streltsov.todolist.data.provides

import com.google.firebase.auth.FirebaseAuth
import ru.streltsov.todolist.data.repository.UserRepositoryImpl
import javax.inject.Inject

class UserProvider @Inject constructor(private val mAuth: FirebaseAuth) : UserProviderImpl {

  override fun signInByEmail(email: String, password: String, _callback: UserRepositoryImpl.UserCallback) {
    mAuth.signInWithEmailAndPassword(email, password)
        .addOnSuccessListener {
          _callback.onSuccess(it.user!!)
        }
        .addOnFailureListener {
          _callback.onError(it)
        }
  }

  override fun signUpByEmail(email: String, password: String, _callback: UserRepositoryImpl.UserCallback) {
    mAuth.createUserWithEmailAndPassword(email, password)
        .addOnSuccessListener {
          _callback.onSuccess(it.user!!)
        }
        .addOnFailureListener {
          _callback.onError(it)
        }
  }


}