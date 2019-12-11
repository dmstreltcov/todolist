package ru.streltsov.todolist.ui.tasklist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_task_list.*
import ru.streltsov.todolist.R
import ru.streltsov.todolist.ui.task.TaskActivity


class TaskListActivity : AppCompatActivity(), TaskListView, TaskListAdapter.Callback {

    private val CREATE_TASK = 1001
    private val OPEN_TASK = 1002
    private val TASK_SAVED = 1412
    private val TASK_DELETED = 1413
    private val TAG: String = "TaskListActivity"
    private val presenter: TaskListPresenter by lazy { TaskListPresenter() }
    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayout: LinearLayoutManager
    private lateinit var adapter: TaskListAdapter
    private lateinit var query: Query
    private lateinit var options: FirestoreRecyclerOptions<Task>
    private lateinit var addTaskBtn: FloatingActionButton
    private lateinit var actionBarToolbar: BottomAppBar
    private lateinit var progressBar: ProgressBar
    private var currentUser: FirebaseUser? = intent?.getParcelableExtra("user")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        presenter.attach(this)
        initElements()
        setListeners()
        presenter.onLoadData()

    }

    private fun initElements() {
        recyclerView = findViewById(R.id.tasklist)
        addTaskBtn = findViewById(R.id.add_task)
        linearLayout = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayout
        actionBarToolbar = findViewById(R.id.bottomAppBar)
        progressBar = progressBarList
        setSupportActionBar(actionBarToolbar)
    }

    private fun setListeners() {
        addTaskBtn.setOnClickListener {
            startActivityForResult(Intent(this, TaskActivity::class.java), CREATE_TASK)
        }
    }

    override fun initAdapter(taskList:ArrayList<Task>) {
        adapter = TaskListAdapter(taskList)
        adapter.setCallback(this)
        recyclerView.adapter = adapter
        hideProgressBar() //TODO переделать, очень плохо
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
            CREATE_TASK -> if (resultCode == TASK_SAVED) Toast.makeText(
                this,
                "Задача создана",
                Toast.LENGTH_SHORT
            ).show()
            OPEN_TASK -> if(resultCode == TASK_DELETED) adapter.notifyDataSetChanged()
        }
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE

        tasklist.visibility = View.GONE
        bottomAppBar.visibility = View.GONE
        add_task.hide()
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE

        tasklist.visibility = View.VISIBLE
        bottomAppBar.visibility = View.VISIBLE
        add_task.show()
    }

    override fun getContext(): Context = this

    override fun onStart() {
        super.onStart()
//        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
//        adapter.stopListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    override fun onItemClicked(item: Task) {
        val intent: Intent = Intent(this, TaskActivity::class.java)
        intent.putExtra("taskID", item.id)
        startActivityForResult(intent, OPEN_TASK)
    }

    override fun onStatusChanged(item: Task, status: Boolean) {
        presenter.onChangeStatus(item.id, status)
    }
}
