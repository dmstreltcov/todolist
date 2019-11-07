package ru.streltsov.todolist.ui.tasklist

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import ru.streltsov.todolist.R

class TaskListActivity : AppCompatActivity(), TaskListView {

    private var currentUser: FirebaseUser? = null

    private val TAG: String = "TaskListActivity"
    private val presenter: TaskListPresenter by lazy { TaskListPresenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "OnCreate()")
        setContentView(R.layout.activity_task_list)
        currentUser = intent?.getParcelableExtra("user") // <- вот тут мне кажется чепуха
    }

    override fun showError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getContext(): Context = this

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "OnResume()")
    }
}
