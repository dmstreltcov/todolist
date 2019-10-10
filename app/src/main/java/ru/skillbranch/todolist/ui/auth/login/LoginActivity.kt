package ru.skillbranch.todolist.ui.auth.login

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import ru.skillbranch.todolist.R

class LoginActivity : AppCompatActivity(), LoginView {


    private val TAG: String = "LoginActivity"
    private val presenter:ILoginPresenter<LoginView> by lazy { LoginPresenter(this) }

    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var loginBtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    override fun getContext(): Context {
        return getContext()
    }


    fun init(){
        email = findViewById(R.id.email_input)
        password = findViewById(R.id.password_input)
        loginBtn = findViewById(R.id.login_btn)
    }

    override fun login() {
        presenter.onLoginButton(email.text.toString(), password.text.toString())
    }


}
