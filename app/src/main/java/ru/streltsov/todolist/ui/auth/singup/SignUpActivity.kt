package ru.streltsov.todolist.ui.auth.singup

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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import ru.streltsov.todolist.R
import ru.streltsov.todolist.ui.tasklist.TaskListActivity
import com.google.android.gms.common.api.ApiException
import ru.streltsov.todolist.MainActivity

class SignUpActivity : AppCompatActivity(), SignUpView {

    private val TAG: String = "TODO _SignUpActivity"
    private val presenter: SignUpPresenter by lazy { SignUpPresenter() }
    private lateinit var signUp: Button
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var progressBar:ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        Log.d(TAG, "TODO _OnCreate()")
        presenter.attach(this)
        init()
    }

    private fun init() {
        signUp = findViewById(R.id.sign_up_btn)
        emailInput = findViewById(R.id.email_input)
        passwordInput = findViewById(R.id.password_input)
        progressBar=findViewById(R.id.progressBar)

        signUp.setOnClickListener {
            presenter.onSignUp(emailInput.text.toString(), passwordInput.text.toString())
        }
    }

    override fun updateUI(user: FirebaseUser) {
        Log.d(TAG, "TODO _Update UI")
        val intent: Intent = MainActivity.createTaskListIntent(this, user)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
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
        Log.d(TAG, "TODO _OnDestroy()")
        presenter.detach()
    }
}
