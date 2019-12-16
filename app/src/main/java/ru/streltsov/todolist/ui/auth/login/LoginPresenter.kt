package ru.streltsov.todolist.ui.auth.login

import android.util.Log
import ru.streltsov.todolist.data.DataBase
import ru.streltsov.todolist.data.FirebaseDB
import com.google.firebase.auth.FirebaseUser
import ru.streltsov.todolist.base.BasePresenter
import ru.streltsov.todolist.data.Validator


class LoginPresenter : BasePresenter<LoginView>(), Validator {

    private val TAG: String = "TodoList/LoginPresenter"
    private var db: DataBase = FirebaseDB()

    fun onLoginButton(email: String, password: String) {
        view?.showProgress()
        validate(email, password)
        if (validate(email, password)) {
            db.login(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "TodoList/Login with email: success")
                    view?.updateUI(db.currentUser() as FirebaseUser)
                } else {
                    Log.d(TAG, "TodoList/Login with email: failed")
                    view?.showMessage("Такой пользователь отсутствует")
                    view?.hideProgress()
                }
            }
        }
    }

    fun onSignUpButton() {
        view?.signUp()
    }

    override fun validate(vararg args: String): Boolean {
        val email = args[0]
        val password = args[1]
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