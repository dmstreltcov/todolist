package ru.streltsov.todolist.data.provides.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import ru.streltsov.todolist.data.repository.auth.UserRepositoryImpl
import javax.inject.Inject

class UserProvider @Inject constructor(private val mAuth: FirebaseAuth) : UserProviderImpl {

  override fun signInByEmail(email: String, password: String, callback: UserRepositoryImpl.UserCallback) {
    mAuth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener {
            if(it.isSuccessful){
                Log.d("TaskListProvider", "${it.result?.user?.uid}")
                callback.onSuccess()
            }else{
                callback.onError(it.exception!!)
            }
        }
  }

  override fun signUpByEmail(email: String, password: String, callback: UserRepositoryImpl.UserCallback) {
    mAuth.createUserWithEmailAndPassword(email, password)
        .addOnSuccessListener {
          callback.onSuccess()
        }
        .addOnFailureListener {
          callback.onError(it)
        }
  }


}