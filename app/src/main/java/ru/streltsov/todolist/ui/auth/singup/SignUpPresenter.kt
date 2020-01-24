package ru.streltsov.todolist.ui.auth.singup

import ru.streltsov.todolist.ui.base.BasePresenter
import ru.streltsov.todolist.data.Validator
import ru.streltsov.todolist.data.repository.auth.UserRepositoryImpl
import javax.inject.Inject

class SignUpPresenter @Inject constructor(private val repository: UserRepositoryImpl) : BasePresenter<SignUpView>(), Validator {

    private val TAG: String = "TodoList/SignUpPresenter"

    fun onSignUp(email: String, password: String) {
        view?.showProgress()
        if (validate(email, password)) {
            repository.signUp(email, password)
            view?.updateUI()
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
}