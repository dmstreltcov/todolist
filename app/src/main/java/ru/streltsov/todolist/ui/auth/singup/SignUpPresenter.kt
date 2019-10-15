package ru.streltsov.todolist.ui.auth.singup

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import ru.streltsov.todolist.base.BasePresenter
import ru.streltsov.todolist.data.DataBase
import ru.streltsov.todolist.data.FirebaseDB

class SignUpPresenter<T> : BasePresenter<SignUpView>() {

    private val TAG: String = "SignUpPresenter"
    private var db: DataBase = FirebaseDB()

    fun onSignUp(email: String, password: String) {
        db.signUp(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "Signup with email: success")
                view?.updateUI(db.currentUser() as FirebaseUser)
            } else {
                Log.d(TAG, "Signup with email: failed")
                Toast.makeText(view?.getContext(), "Something goes wrong", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun onGoogleSignUp() {

    }

}