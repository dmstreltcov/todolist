package ru.skillbranch.todolist.ui.auth.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.skillbranch.todolist.R

class LoginActivity : AppCompatActivity(), LoginView {

    private val TAG: String = "LoginActivity"
    private val presenter: ILoginPresenter by lazy { LoginPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun login() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
