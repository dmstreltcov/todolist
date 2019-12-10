package ru.streltsov.todolist.ui.tasklist

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.Query
import ru.streltsov.todolist.R
import ru.streltsov.todolist.ui.task.TaskActivity


class TaskListActivity : AppCompatActivity(), TaskListView, TaskListAdapter.Callback {

    private val REQUEST_CODE = 1001
    private val TAG: String = "TaskListActivity"
    private val presenter: TaskListPresenter by lazy { TaskListPresenter() }
    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayout: LinearLayoutManager
    private lateinit var adapter: TaskListAdapter
    private lateinit var query: Query
    private lateinit var options: FirestoreRecyclerOptions<Task>
    private lateinit var addTaskBtn: FloatingActionButton
    private lateinit var actionBarToolbar: BottomAppBar
    private var currentUser: FirebaseUser? = intent?.getParcelableExtra("user")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "TodoList/OnCreate()")
        setContentView(R.layout.activity_task_list)
        presenter.attach(this)
        initElements()
        setListeners()
        initAdapter()
    }

    private fun initElements() {
        recyclerView = findViewById(R.id.tasklist)
        addTaskBtn = findViewById(R.id.add_task)
        linearLayout = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayout
        actionBarToolbar = findViewById(R.id.bottomAppBar)
        setSupportActionBar(actionBarToolbar)
    }

    private fun setListeners() {
        addTaskBtn.setOnClickListener {
            startActivityForResult(Intent(this, TaskActivity::class.java), REQUEST_CODE)
        }
    }

    private fun initAdapter() {
        query = presenter.onLoadData()
        options = FirestoreRecyclerOptions.Builder<Task>()
            .setQuery(query, Task::class.java)
            .build()  //хм
        adapter = TaskListAdapter(options)
        adapter.setCallback(this)
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy <= 0 && !addTaskBtn.isShown) addTaskBtn.show() else if (dy > 0 && addTaskBtn.isShown) addTaskBtn.hide()
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                addTaskBtn.isShown
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_task_list, menu)
        return true
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1001 -> if (resultCode == Activity.RESULT_OK) Toast.makeText(
                this,
                "Задача создана",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getContext(): Context = this

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    override fun onItemClicked(item: Task) {
        val intent: Intent = Intent(this, TaskActivity::class.java)
        intent.putExtra("taskID", item.id)
        startActivity(intent)
    }

    override fun onStatusChanged(item: Task, status: Boolean) {
        presenter.onChangeStatus(item.id, status)
    }
}
