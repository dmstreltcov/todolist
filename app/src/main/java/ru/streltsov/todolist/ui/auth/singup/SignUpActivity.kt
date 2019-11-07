package ru.streltsov.todolist.ui.auth.singup

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
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

    private val TAG: String = "TAG_SignUpActivity"
    private val presenter: SignUpPresenter by lazy { SignUpPresenter() }
    private lateinit var signUp: Button
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        Log.d(TAG, "OnCreate()")
        presenter.attach(this)
        init()
    }

    private fun init() {
        signUp = findViewById(R.id.sign_up_btn)
        emailInput = findViewById(R.id.email_input)
        passwordInput = findViewById(R.id.password_input)

        signUp.setOnClickListener {
            presenter.onSignUp(emailInput.text.toString(), passwordInput.text.toString())
        }
    }

    override fun updateUI(user: FirebaseUser) {
        Log.d(TAG, "Update UI")
        val intent: Intent = MainActivity.createTaskListIntent(this, user)
        startActivity(intent)
        finish()
    }

    override fun getContext(): Context = this

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "OnDestroy()")
        presenter.detach()
    }
}
