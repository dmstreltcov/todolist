package ru.skillbranch.todolist.ui.tasklist

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseUser
import ru.skillbranch.todolist.R

class TaskListActivity : AppCompatActivity(), TaskListView {

    private lateinit var currentUser: FirebaseUser
    private val TAG: String = "TaskListActivity"
    private val presenter: ITaskListPresenter<TaskListView> by lazy { TaskListPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        currentUser = intent.getParcelableExtra("user")!! // <- вот тут мне кажется чепуха


    }

    override fun getContext(): Context = this


}
