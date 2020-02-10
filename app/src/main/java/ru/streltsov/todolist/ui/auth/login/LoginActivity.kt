package ru.streltsov.todolist.ui.auth.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import ru.streltsov.todolist.App
import ru.streltsov.todolist.R
import ru.streltsov.todolist.ui.auth.singup.SignUpActivity
import ru.streltsov.todolist.ui.tasklist.TaskListActivity
import javax.inject.Inject

const val TAG: String = "LoginActivity"

class LoginActivity : AppCompatActivity(), LoginView {
  private lateinit var email: TextInputEditText
  private lateinit var password: TextInputEditText
  private lateinit var loginBtn: Button
  private lateinit var signUpBtn: TextView
  private lateinit var forgotPassword: TextView
  private lateinit var enterByGoogle: TextView
//  private lateinit var progressBar: ProgressBar

  @Inject
  lateinit var presenter: LoginPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    App.instance.getAppComponent().inject(this)
    presenter.attach(this)
    init()
  }

  private fun init() {
    email = findViewById(R.id.email_et_input)
    password = findViewById(R.id.password_et_input)
    loginBtn = findViewById(R.id.enter_btn)
    signUpBtn = findViewById(R.id.registration_link)
    forgotPassword = findViewById(R.id.forgot_password_link)
    enterByGoogle = findViewById(R.id.enter_by_google_link)
//    progressBar = findViewById(R.id.progressBar)

    loginBtn.setOnClickListener {
      presenter.onLoginButton(email.text.toString(), password.text.toString())
    }
    signUpBtn.setOnClickListener {
      presenter.onSignUpButton()
    }
  }

  override fun signUp() {
    val intent = Intent(this, SignUpActivity::class.java)
    startActivity(intent)
  }

  override fun showProgress() {
    loginBtn.visibility = View.GONE
//    progressBar.visibility = View.VISIBLE
  }

  override fun hideProgress() {
    loginBtn.visibility = View.VISIBLE
//    progressBar.visibility = View.GONE
  }

  override fun updateUI() {
    Log.d(TAG, "Update UI")
    val intent = Intent(this, TaskListActivity::class.java)
    startActivity(intent)
    finish()
  }

  override fun showMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
  }

  override fun getContext(): Context = this

  override fun onDestroy() {
    super.onDestroy()
    Log.d(TAG, "onDestroy()")
    presenter.detach()
  }
}
