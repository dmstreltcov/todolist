package ru.skillbranch.todolist.ui.auth.login

import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import ru.skillbranch.todolist.data.DataBase
import ru.skillbranch.todolist.data.FirebaseDB
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import ru.skillbranch.todolist.base.BasePresenter


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

    private fun validate(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            Toast.makeText(view?.getContext(), "Введите Email", Toast.LENGTH_SHORT).show()
            return false
        } else if (password.isEmpty()) {
            Toast.makeText(view?.getContext(), "Введите пароль", Toast.LENGTH_SHORT).show()
            return false
        } else {
            return true
        }
    }
}