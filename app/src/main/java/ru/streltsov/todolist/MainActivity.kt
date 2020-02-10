package ru.streltsov.todolist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import ru.streltsov.todolist.ui.auth.login.LoginActivity
import ru.streltsov.todolist.ui.tasklist.FragmentHolder
import javax.inject.Inject

const val TAG: String = "TodoList/MainActivity"
class MainActivity : AppCompatActivity() {

  @Inject
  lateinit var mAuth: FirebaseAuth

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    App.instance.getAppComponent().inject(this)
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
    val intent = Intent(this, LoginActivity::class.java)
    startActivity(intent)
    finish()
  }

  private fun onTaskListPage() {
    Log.d(TAG, "Переход на страницу со списком задач")
    val intent = Intent(this, FragmentHolder::class.java)
    startActivity(intent)
    finish()
  }
}
