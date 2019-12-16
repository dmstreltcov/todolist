package ru.streltsov.todolist.ui.auth.singup

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import ru.streltsov.todolist.base.BasePresenter
import ru.streltsov.todolist.data.DataBase
import ru.streltsov.todolist.data.FirebaseDB
import ru.streltsov.todolist.data.Validator

class SignUpPresenter : BasePresenter<SignUpView>(), Validator {

    private val TAG: String = "TodoList/SignUpPresenter"
    private var db: DataBase = FirebaseDB()

    fun onSignUp(email: String, password: String) {
        view?.showProgress()
//        Thread.sleep(1000)
        if (validate(email, password)) {
            db.signUp(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "TodoList/SignUp with email: success")
                    view?.updateUI(db.currentUser() as FirebaseUser)
                } else {
                    Log.d(TAG, "TodoList/Signup with email: failed")
                    view?.showMessage("Не удалось зарегистрироваться")
                    view?.hideProgress()
                }
            }
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