package ru.skillbranch.todolist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import ru.skillbranch.todolist.R
import ru.skillbranch.todolist.ui.auth.login.LoginActivity
import ru.skillbranch.todolist.ui.auth.singup.SingUpActivity
import ru.skillbranch.todolist.ui.tasklist.TaskListActivity

class MainActivity : AppCompatActivity() {

    private val TAG: String = "MainActivity"
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        val currentUser: FirebaseUser? = mAuth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        when(currentUser){
            null -> onLoginPage()
            else -> onTaskListPage(currentUser)
        }
    }

    private fun onSingUpPage() {
        val intent: Intent = Intent(this, SingUpActivity::class.java)
        startActivity(intent)
    }

    private fun onLoginPage() {
        val intent: Intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun onTaskListPage(currentUser: FirebaseUser?){
        val intent: Intent = Intent(this, TaskListActivity::class.java)
        intent.putExtra("currentUser", currentUser)
        startActivity(intent)
    }
}
