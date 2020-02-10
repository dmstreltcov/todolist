package ru.streltsov.todolist.data.provides

import com.google.firebase.auth.FirebaseAuth
import ru.streltsov.todolist.data.provides.impl.UserProvider
import ru.streltsov.todolist.ui.base.Callback
import javax.inject.Inject

class UserProviderImpl @Inject constructor(private val auth: FirebaseAuth) :
    UserProvider {

    override fun signInByEmail(email: String, password: String, callback: Callback) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                callback.onSuccess()
            }
            .addOnFailureListener {
                callback.onError(it)
            }
        }


    override fun signUpByEmail(email: String, password: String, callback: Callback) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                callback.onSuccess()
            }
            .addOnFailureListener {
                callback.onError(it)
            }

    }


}