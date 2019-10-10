package ru.skillbranch.todolist.ui.auth.login

import com.google.android.gms.tasks.OnCompleteListener
import ru.skillbranch.todolist.data.DataBase
import ru.skillbranch.todolist.data.FirebaseDB
import com.google.firebase.auth.AuthResult


class LoginPresenter(_view: LoginView) : ILoginPresenter<LoginView>() {

    private val TAG: String = "LoginPresenter"
    private var db:DataBase = FirebaseDB()

    override fun onLoginButton(email:String, password:String) {
        db.login(email, password).addOnCompleteListener{task ->
            if (task.isSuccessful){

            }
            else{

            }
        }
    }

    override fun onSingupButton() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRestorePassword() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}