package ru.streltsov.todolist.ui.auth.login

import android.util.Log
import ru.streltsov.todolist.data.DataBase
import ru.streltsov.todolist.data.FirebaseDB
import com.google.firebase.auth.FirebaseUser
import ru.streltsov.todolist.base.BasePresenter


class LoginPresenter : BasePresenter<LoginView>() {

    private val TAG: String = "TODO _LoginPresenter"
    private var db: DataBase = FirebaseDB()

    fun onLoginButton(email: String, password: String) {
        view?.showProgress()
        if (validate(email, password)) {
            db.login(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "TODO _Login with email: success")
                    view?.updateUI(db.currentUser() as FirebaseUser)
                } else {
                    Log.d(TAG, "TODO _Login with email: failed")
                    view?.showMessage("Такой пользователь отсутствует")
                    view?.hideProgress()
                }
            }
        }
    }

    fun onSignUpButton() {
        view?.signUp()
    }

    private fun validate(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                view?.showMessage("Введите email")
                false
            }
            password.isEmpty() -> {
                view?.showMessage("Введите пароль")
                false
            }
            else -> true
        }
    }
}