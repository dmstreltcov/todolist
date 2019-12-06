package ru.streltsov.todolist.ui.auth.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import ru.streltsov.todolist.MainActivity
import ru.streltsov.todolist.R
import ru.streltsov.todolist.ui.auth.singup.SignUpActivity

class LoginActivity : AppCompatActivity(), LoginView {

    private val TAG: String = "TAG_LoginActivity"
    private lateinit var mAuth: FirebaseAuth

    private val presenter: LoginPresenter by lazy { LoginPresenter() }

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginBtn: Button
    private lateinit var signUpBtn: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter.attach(this)
        init()
        mAuth = FirebaseAuth.getInstance()
        Log.d(TAG, "${mAuth.currentUser}")
    }

    private fun init() {
        email = findViewById(R.id.email_input)
        password = findViewById(R.id.password_input)
        loginBtn = findViewById(R.id.log_in_btn)
        signUpBtn = findViewById(R.id.sign_up_btn)
        progressBar = findViewById(R.id.progressBar)

        loginBtn.setOnClickListener {
            presenter.onLoginButton(email.text.toString(), password.text.toString())
        }
        signUpBtn.setOnClickListener {
            presenter.onSignUpButton()
        }
    }

    override fun signUp() {
        val intent: Intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    override fun showProgress() {
        loginBtn.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        loginBtn.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun updateUI(user: FirebaseUser) {
        Log.d(TAG, "TODO _Update UI")
        val intent: Intent = MainActivity.createTaskListIntent(
            this,
            user
        ) // Не уверен насчет такого решения, но так сделал
        startActivity(intent)
        finish()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun getContext(): Context = this

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "TODO _onDestroy()")
        presenter.detach()
    }
}
