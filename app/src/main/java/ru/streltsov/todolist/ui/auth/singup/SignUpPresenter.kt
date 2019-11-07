package ru.streltsov.todolist.ui.auth.singup

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseUser
import ru.streltsov.todolist.base.BasePresenter
import ru.streltsov.todolist.data.DataBase
import ru.streltsov.todolist.data.FirebaseDB
import kotlin.math.sign

class SignUpPresenter : BasePresenter<SignUpView>() {

    private val TAG: String = "SignUpPresenter"
    private var db: DataBase = FirebaseDB()

    fun onSignUp(email: String, password: String) {
        if (validate(email, password)) {
            db.signUp(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "SignUp with email: success")
                    view?.updateUI(db.currentUser() as FirebaseUser)
                } else {
                    Log.d(TAG, "Signup with email: failed")
                    view?.showError("Не удалось зарегистрироваться")
                }
            }
        }
    }

    private fun validate(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                view?.showError("Введите email")
                false
            }
            password.isEmpty() -> {
                view?.showError("Введите пароль")
                false
            }
            else -> true
        }
    } //Не выполняется принцип DRY. Решение, которое я могу предложить: Создать абстрактный класс, например AuthPresenter : BasePresenter<V: BaseView> и в нем метод на валидацию
}