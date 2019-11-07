package ru.streltsov.todolist.ui.auth.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import ru.streltsov.todolist.MainActivity
import ru.streltsov.todolist.R
import ru.streltsov.todolist.ui.auth.singup.SignUpActivity

class LoginActivity : AppCompatActivity(), LoginView {

    private val TAG: String = "TAG_LoginActivity"
    private val presenter: LoginPresenter by lazy { LoginPresenter() }

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginBtn: Button
    private lateinit var signUpBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter.attach(this)
        init()
    }

    private fun init() {
        email = findViewById(R.id.email_input)
        password = findViewById(R.id.password_input)
        loginBtn = findViewById(R.id.log_in_btn)
        signUpBtn = findViewById(R.id.sign_up_btn)

        loginBtn.setOnClickListener {
            presenter.onLoginButton(email.text.toString(), password.text.toString())
        }
        signUpBtn.setOnClickListener {
            presenter.onSignUpButton()
        }
    }

    override fun signUp(){
        val intent: Intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    override fun updateUI(user: FirebaseUser) {
        Log.d(TAG, "Update UI")
        val intent: Intent = MainActivity.createTaskListIntent(this,user) // Не уверен насчет такого решения, но так сделал
        startActivity(intent)
        finish()
    }

    override fun showError(message:String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun getContext(): Context = this

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy()")
        presenter.detach()
    }
}
