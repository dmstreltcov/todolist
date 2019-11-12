package ru.streltsov.todolist.ui.tasklist

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.google.firebase.auth.FirebaseUser
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import ru.streltsov.todolist.R
import ru.streltsov.todolist.ui.task.TaskActivity


class TaskListActivity : AppCompatActivity(), TaskListView {

    private var currentUser: FirebaseUser? = null

    private val TAG: String = "TaskListActivity"
    private val presenter: TaskListPresenter by lazy { TaskListPresenter() }
    private lateinit var recyclerView: RecyclerView
    lateinit var linearLayout: LinearLayoutManager
    private lateinit var adapter: TaskListAdapter
    private lateinit var query: Query
    private lateinit var options: FirestoreRecyclerOptions<Task>
    private lateinit var addTaskBtn:FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "OnCreate()")
        setContentView(R.layout.activity_task_list)
        currentUser = intent?.getParcelableExtra("user") // <- вот тут мне кажется чепуха

        init()
        loadData()


    }

    private fun init(){
        recyclerView = findViewById(R.id.tasklist)
        addTaskBtn = findViewById(R.id.add_task)
        linearLayout = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayout
        addTaskBtn.setOnClickListener {
            addNewTask()
        }
    }

    override fun loadData() {
        query = presenter.onLoadData()
        options = FirestoreRecyclerOptions.Builder<Task>()
            .setQuery(query, Task::class.java)
            .build()  //хм
        adapter = TaskListAdapter(options)
        recyclerView.adapter = adapter

    }

    private fun addNewTask(){
        startActivity(Intent(this, TaskActivity::class.java))
    }

    override fun showError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getContext(): Context = this

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "OnResume()")
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}
