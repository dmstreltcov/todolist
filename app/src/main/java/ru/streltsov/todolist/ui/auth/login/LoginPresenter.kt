package ru.streltsov.todolist.ui.auth.login

import android.util.Log
import ru.streltsov.todolist.data.FirebaseRepository
import com.google.firebase.auth.FirebaseUser
import ru.streltsov.todolist.MainActivity
import ru.streltsov.todolist.base.BasePresenter
import ru.streltsov.todolist.data.Validator
import ru.streltsov.todolist.data.repository.Callback
import ru.streltsov.todolist.data.repository.UserRepository
import javax.inject.Inject


class LoginPresenter @Inject constructor(private val db:UserRepository)  : BasePresenter<LoginView>(), Validator, UserRepository.UserCallback {

    private val TAG: String = "TodoList/LoginPresenter"

    fun onLoginButton(email: String, password: String) {
        if (validate(email, password)) {
            view?.showProgress()
            db.login(email, password, this)
        }
    }

    override fun onSuccess() {
        Log.d(TAG, "TodoList/Login with email: success")
        view?.updateUI()
    }

    override fun onError() {
        Log.d(TAG, "TodoList/Login with email: failed")
        view?.showMessage("Такой пользователь отсутствует")
        view?.hideProgress()
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