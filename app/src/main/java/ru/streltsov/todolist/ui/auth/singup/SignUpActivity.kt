package ru.streltsov.todolist.ui.auth.singup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.streltsov.todolist.App
import ru.streltsov.todolist.R
import ru.streltsov.todolist.ui.tasklist.FragmentHolder
import javax.inject.Inject

const val TAG: String = "SignUpActivity"
class SignUpActivity : AppCompatActivity(), SignUpView {

  private lateinit var signUp: Button
  private lateinit var emailInput: EditText
  private lateinit var passwordInput: EditText
  private lateinit var progressBar: ProgressBar

  @Inject
  lateinit var presenter: SignUpPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_sign_up)
    App.instance.getAppComponent().inject(this)
    presenter.attach(this)
    init()
  }

  private fun init() {
    signUp = findViewById(R.id.sign_up_btn)
    emailInput = findViewById(R.id.email_input)
    passwordInput = findViewById(R.id.password_input)
    progressBar = findViewById(R.id.progressBar)

    signUp.setOnClickListener {
      presenter.onSignUp(emailInput.text.toString(), passwordInput.text.toString())
    }
  }

  override fun updateUI() {
    Log.d(TAG, "TodoList/Update UI")
    val intent = Intent(this, FragmentHolder::class.java)
    startActivity(intent)
    finish()
  }

  override fun showProgress() {
    signUp.visibility = View.GONE
    progressBar.visibility = View.VISIBLE
  }

  override fun hideProgress() {
    signUp.visibility = View.VISIBLE
    progressBar.visibility = View.GONE
  }

  override fun getContext(): Context = this

  override fun showMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
  }

  override fun onDestroy() {
    super.onDestroy()
    Log.d(TAG, "TodoList/OnDestroy()")
    presenter.detach()
  }
}
