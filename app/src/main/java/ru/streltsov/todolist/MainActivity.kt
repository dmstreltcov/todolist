package ru.streltsov.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import ru.streltsov.todolist.ui.auth.login.LoginActivity
import ru.streltsov.todolist.ui.tasklist.TaskListActivity

class MainActivity : AppCompatActivity() {

    private val TAG: String = "MainActivity"
    private lateinit var mAuth: FirebaseAuth
    private var account:GoogleSignInAccount? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()

    }

    override fun onStart() {
        super.onStart()
        val currentUser: FirebaseUser? = mAuth.currentUser
        account = GoogleSignIn.getLastSignedInAccount(this)

        if (currentUser != null) {
            updateUI(currentUser)
        }else{
            updateUI(account)
        }
    }

    private fun updateUI(currentUser: AbstractSafeParcelable?) {
        when (currentUser) {
            null -> onLoginPage()
            else -> onTaskListPage(currentUser)
        }
    }
//    private fun updateUI(currentAccount: GoogleSignInAccount?) {
//        when (currentAccount) {
//            null -> onLoginPage()
//            else -> onTaskListPage(currentAccount)
//        }
//    }

    private fun onLoginPage() {
        Log.d(TAG, "Переход на страницу авторизации")
        val intent: Intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun onTaskListPage(currentUser: AbstractSafeParcelable?) {
        Log.d(TAG, "Переход на страницу со списком задач")
        val intent: Intent = Intent(this, TaskListActivity::class.java)
        when(currentUser){
            is FirebaseUser -> intent.putExtra("user", currentUser)
            is GoogleSignInAccount -> intent.putExtra("account", currentUser)
        }
        startActivity(intent)
        finish()
    }

//    private fun onTaskListPage(currentAccount: GoogleSignInAccount) {
//        Log.d(TAG, "Переход на страницу со списком задач")
//        val intent: Intent = Intent(this, TaskListActivity::class.java)
//        intent.putExtra("account", currentAccount)
//        startActivity(intent)
//        finish()
//    }
}
