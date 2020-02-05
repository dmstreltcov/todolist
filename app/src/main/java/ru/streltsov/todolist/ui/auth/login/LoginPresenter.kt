package ru.streltsov.todolist.ui.auth.login

import ru.streltsov.todolist.data.Validator
import ru.streltsov.todolist.ui.base.BasePresenter
import ru.streltsov.todolist.ui.base.Callback
import ru.streltsov.todolist.usecases.SignInUseCase
import java.lang.Exception
import javax.inject.Inject


class LoginPresenter @Inject constructor(val usecase:SignInUseCase) :
    BasePresenter<LoginView>(), Validator, Callback {

    private val TAG: String = "TodoList/LoginPresenter"

    fun onLoginButton(email: String, password: String) {
        if (validate(email, password)) {
            usecase(email, password, this)
            view?.showProgress()
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

    override fun onSuccess() {
        view?.updateUI()
    }

    override fun onError(exception: Exception) {
        exception.printStackTrace()
    }
}