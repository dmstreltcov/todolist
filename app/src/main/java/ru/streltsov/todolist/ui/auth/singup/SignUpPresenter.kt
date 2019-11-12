package ru.streltsov.todolist.ui.auth.singup

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import ru.streltsov.todolist.base.BasePresenter
import ru.streltsov.todolist.data.DataBase
import ru.streltsov.todolist.data.FirebaseDB

class SignUpPresenter : BasePresenter<SignUpView>() {

    private val TAG: String = "TODO _SignUpPresenter"
    private var db: DataBase = FirebaseDB()

    fun onSignUp(email: String, password: String) {
        if (validate(email, password)) {
            db.signUp(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "TODO _SignUp with email: success")
                    view?.updateUI(db.currentUser() as FirebaseUser)
                } else {
                    Log.d(TAG, "TODO _Signup with email: failed")
                    view?.showMessage("Не удалось зарегистрироваться")
                }
            }
        }
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
    } //Не выполняется принцип DRY. Решение, которое я могу предложить: Создать абстрактный класс, например AuthPresenter : BasePresenter<V: BaseView> и в нем метод на валидацию
}