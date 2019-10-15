package ru.streltsov.todolist.ui.tasklist

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseUser
import ru.streltsov.todolist.R

class TaskListActivity : AppCompatActivity(), TaskListView {

    private lateinit var currentUser: FirebaseUser
    private val TAG: String = "TaskListActivity"
    private val presenter: TaskListPresenter<TaskListView> by lazy { TaskListPresenter<TaskListView>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        currentUser = intent.getParcelableExtra("user")!! // <- вот тут мне кажется чепуха


    }

    override fun getContext(): Context = this


}
