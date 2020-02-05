package ru.streltsov.todolist.ui.auth.singup

import ru.streltsov.todolist.ui.base.BasePresenter
import ru.streltsov.todolist.data.Validator
import ru.streltsov.todolist.data.provides.impl.UserProvider
import ru.streltsov.todolist.ui.base.Callback
import java.lang.Exception
import javax.inject.Inject

class SignUpPresenter @Inject constructor(private val provider: UserProvider) :
    BasePresenter<SignUpView>(), Validator, Callback {

    private val TAG: String = "TodoList/SignUpPresenter"

    fun onSignUp(email: String, password: String) {
        view?.showProgress()
        if (validate(email, password)) {
            provider.signUpByEmail(email, password, this)
        }
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