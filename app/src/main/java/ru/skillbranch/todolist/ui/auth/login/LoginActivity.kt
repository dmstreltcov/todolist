package ru.skillbranch.todolist.ui.auth.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseUser
import ru.skillbranch.todolist.R
import ru.skillbranch.todolist.ui.auth.singup.SignUpActivity
import ru.skillbranch.todolist.ui.tasklist.TaskListActivity

class LoginActivity : AppCompatActivity(), LoginView {

    private val TAG: String = "LoginActivity"
    private val presenter: LoginPresenter<LoginView> by lazy { LoginPresenter<LoginView>() }

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginBtn: Button
    private lateinit var signUpBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter.attach(this)
        init()

        loginBtn.setOnClickListener {
            presenter.onLoginButton(email.text.toString(), password.text.toString())
        }
        signUpBtn.setOnClickListener {
            presenter.onSignupButton()
        }
    }

    private fun init() {
        email = findViewById(R.id.email_input)
        password = findViewById(R.id.password_input)
        loginBtn = findViewById(R.id.log_in_btn)
        signUpBtn = findViewById(R.id.sing_up_btn)
    }

    override fun signUp(){
        val intent: Intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    override fun updateUI(user: FirebaseUser) {
        Log.d(TAG, "Update UI")
        val intent: Intent = Intent(this, TaskListActivity::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
        finish()
    }

    override fun getContext(): Context = this

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy()")
        presenter.detach()
    }
}
