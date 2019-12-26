package ru.streltsov.todolist

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import ru.streltsov.todolist.ui.auth.login.LoginActivity
import ru.streltsov.todolist.ui.auth.login.LoginPresenter
import ru.streltsov.todolist.ui.auth.login.di.DaggerLoginComponent
import ru.streltsov.todolist.ui.auth.login.di.LoginComponent
import ru.streltsov.todolist.ui.tasklist.TaskListActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val TAG: String = "TodoList/MainActivity"
    private lateinit var mAuth: FirebaseAuth

    companion object{
        val component:LoginComponent = DaggerLoginComponent.builder().build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        updateUI()
    }

    private fun updateUI() {
        when (mAuth.currentUser) {
            null -> onLoginPage()
            else -> onTaskListPage()
        }
    }

    private fun onLoginPage() {
        Log.d(TAG, "Переход на страницу авторизации")
        val intent: Intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun onTaskListPage() {
        Log.d(TAG, "Переход на страницу со списком задач")
        val intent: Intent = Intent(this, TaskListActivity::class.java)
        startActivity(intent)
        finish()
    }
}
