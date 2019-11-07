package ru.streltsov.todolist

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import ru.streltsov.todolist.ui.auth.login.LoginActivity
import ru.streltsov.todolist.ui.tasklist.TaskListActivity

class MainActivity : AppCompatActivity() {

    private val TAG: String = "TAG_MainActivity"
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
        when (currentUser) {
            null -> onLoginPage()
            else -> onTaskListPage(currentUser)
        }
    }

    private fun onLoginPage() {
        Log.d(TAG, "Переход на страницу авторизации")
        val intent: Intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun onTaskListPage(currentUser: FirebaseUser?) {
        Log.d(TAG, "Переход на страницу со списком задач")
        val intent: Intent = createTaskListIntent(this, currentUser)
        startActivity(intent)
        finish()
    }

    companion object {
        fun createTaskListIntent(
            context: Context,
            user: FirebaseUser?
        ): Intent {
            return Intent(context, TaskListActivity::class.java).putExtra("user", user)
        }
    }
}
