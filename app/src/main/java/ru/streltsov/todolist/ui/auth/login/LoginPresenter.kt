package ru.streltsov.todolist.ui.auth.login

import android.util.Log
import android.widget.Toast
import ru.streltsov.todolist.data.DataBase
import ru.streltsov.todolist.data.FirebaseDB
import com.google.firebase.auth.FirebaseUser
import ru.streltsov.todolist.base.BasePresenter


class LoginPresenter<T> : BasePresenter<LoginView>() {

    private val TAG: String = "LoginPresenter"
    private var db: DataBase = FirebaseDB()

     fun onLoginButton(email: String, password: String) {
        if (validate(email, password)) {
            db.login(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Login with email: success")
                    view?.updateUI(db.currentUser() as FirebaseUser)
                } else {
                    Log.d(TAG, "Login with email: failed")
                    Toast.makeText(view?.getContext(), "Something goes wrong", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    fun onSignupButton(){
        view?.signUp()
    }

    private fun validate(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                Toast.makeText(view?.getContext(), "Введите Email", Toast.LENGTH_SHORT).show()
                false
            }
            password.isEmpty() -> {
                Toast.makeText(view?.getContext(), "Введите пароль", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }
}