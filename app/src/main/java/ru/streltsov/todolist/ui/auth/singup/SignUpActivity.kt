package ru.streltsov.todolist.ui.auth.singup

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
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



class SignUpActivity : AppCompatActivity(), SignUpView {

    private val TAG: String = "SignUpActivity"
    private val presenter: SignUpPresenter<SignUpView> by lazy { SignUpPresenter<SignUpView>() }
    private lateinit var signUp: Button
    private lateinit var googleSingUp: Button
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText

    private lateinit var gso: GoogleSignInOptions
    private lateinit var googleSignInClient:GoogleSignInClient
    private val RC_SIGN_IN = 9001


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        presenter.attach(this)
        init()

        signUp.setOnClickListener {
            presenter.onSignUp(emailInput.text.toString(), passwordInput.text.toString())
        }
        googleSingUp.setOnClickListener {
            signIn()
        }
    }

    private fun signIn(){
        val signIntIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signIntIntent, RC_SIGN_IN )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN){
            val task:Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            updateUI(account)
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            updateUI(null)
        }

    }

    private fun init() {
        signUp = findViewById(R.id.sign_up_btn)
        googleSingUp = findViewById(R.id.google_sign_up_btn)
        emailInput = findViewById(R.id.email_input)
        passwordInput = findViewById(R.id.password_input)
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)
    }

    override fun updateUI(user: FirebaseUser) {
        Log.d(TAG, "Update UI")
        val intent: Intent = Intent(this, TaskListActivity::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
        finish()
    }

    private fun updateUI(googleSignInAccount: GoogleSignInAccount?){
        Log.d(TAG, "Update UI")
        val intent: Intent = Intent(this, TaskListActivity::class.java)
        intent.putExtra("account", googleSignInAccount)
        startActivity(intent)
        finish()
    }

    override fun getContext(): Context = this

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }
}
