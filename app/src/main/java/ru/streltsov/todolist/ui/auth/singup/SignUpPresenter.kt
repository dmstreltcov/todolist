package ru.streltsov.todolist.ui.auth.singup

import android.util.Log
import ru.streltsov.todolist.ui.base.BasePresenter
import ru.streltsov.todolist.data.FirebaseRepository
import ru.streltsov.todolist.data.Validator
import ru.streltsov.todolist.data.repository.UserRepository
import javax.inject.Inject

class SignUpPresenter @Inject constructor(private val db: UserRepository) : BasePresenter<SignUpView>(), Validator, UserRepository.UserCallback {

    private val TAG: String = "TodoList/SignUpPresenter"

    fun onSignUp(email: String, password: String) {
        view?.showProgress()
        if (validate(email, password)) {
            db.signUp(email, password, this)
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
        Log.d(TAG, "TodoList/SignUp with email: success")
        view?.updateUI()
    }

    override fun onError() {
        Log.d(TAG, "TodoList/Signup with email: failed")
        view?.showMessage("Не удалось зарегистрироваться")
        view?.hideProgress()
    }
}