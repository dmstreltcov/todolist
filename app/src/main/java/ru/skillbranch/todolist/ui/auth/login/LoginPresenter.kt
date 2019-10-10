package ru.skillbranch.todolist.ui.auth.login

import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import ru.skillbranch.todolist.data.DataBase
import ru.skillbranch.todolist.data.FirebaseDB
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser


class LoginPresenter(val _view: LoginView) : ILoginPresenter<LoginView>() {

    private val TAG: String = "LoginPresenter"
    private var db:DataBase = FirebaseDB()

    override fun onLoginButton(email:String, password:String) {
        db.login(email, password).addOnCompleteListener{task ->
            if (task.isSuccessful){
                Log.d(TAG, "Login with email: success")
                _view.updateUI(db.currentUser() as FirebaseUser)
            }
            else{
                Log.d(TAG, "Login with email: failed")
                Toast.makeText(_view.getContext(),"Something goes wrong",Toast.LENGTH_SHORT).show()
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