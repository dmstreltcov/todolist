package ru.streltsov.todolist.ui.auth.login

import ru.streltsov.todolist.data.Validator
import ru.streltsov.todolist.data.repository.auth.UserRepositoryImpl
import ru.streltsov.todolist.ui.base.BasePresenter
import javax.inject.Inject


class LoginPresenter @Inject constructor(private val repository: UserRepositoryImpl) :
    BasePresenter<LoginView>(), Validator, LoginPresenterCallback {

    private val TAG: String = "TodoList/LoginPresenter"

    fun onLoginButton(email: String, password: String) {
        if (validate(email, password)) {
            view?.showProgress()
            repository.signInByEmail(email, password, this)
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

    override fun onError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}