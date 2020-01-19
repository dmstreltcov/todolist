package ru.streltsov.todolist.ui.tasklist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_task_list.*
import ru.streltsov.todolist.App
import ru.streltsov.todolist.MainActivity
import ru.streltsov.todolist.R
import ru.streltsov.todolist.data.Action
import ru.streltsov.todolist.ui.di.module.TaskListModule
import ru.streltsov.todolist.ui.task.TaskActivity
import ru.streltsov.todolist.ui.task.TaskType
import javax.inject.Inject


class TaskListActivity : AppCompatActivity(), TaskListView, TaskListAdapter.Callback {

    //TODO - удалить
    // Навести тут порядок

    private val CREATE_TASK = 1001
    private val OPEN_TASK = 1002
    private val TASK_SAVED = 1412
    private val TASK_DELETED = 1413
    private val TAG: String = "TaskListActivity"
    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayout: LinearLayoutManager
    //TODO зависимость
    private lateinit var adapter: TaskListAdapter
//    @Inject lateinit var _adapter:TaskListAdapter

    private lateinit var addTaskBtn: FloatingActionButton
    private lateinit var actionBarToolbar: BottomAppBar
    private lateinit var progressBar: ProgressBar

    @Inject lateinit var presenter: TaskListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        Log.d(TAG, "Авторизовался")
        App.instance.getTaskListComponent().inject(this)
        presenter.attach(this)
        initElements()
        setListeners()
        presenter.getAllTasks()
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
            val intent = Intent(this, TaskActivity::class.java)
            intent.putExtra("flag", TaskType.NEW)
            startActivityForResult(intent, CREATE_TASK)
        }
    }

    override fun initAdapter(taskList:ArrayList<Task>) {
        adapter = TaskListAdapter()
        adapter.setData(taskList)
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

    override fun updateList(index: Int, action:Action) {
        when(action){
            Action.REMOVED -> adapter.notifyItemRemoved(index)
            Action.MODIFIED -> adapter.notifyItemChanged(index)
            Action.ADDED -> adapter.notifyItemInserted(index)
        }
    }

    override fun addTask(index: Int) {
        adapter.notifyItemInserted(index)
    }

    override fun updateTask(oldIndex: Int, newIndex: Int) {
        if(oldIndex != newIndex){
            adapter.notifyItemMoved(oldIndex, newIndex)
        }
        adapter.notifyItemChanged(newIndex)
    }

    override fun deleteTask(index: Int) {
        adapter.notifyItemRemoved(index)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_task_list, menu)
        return true
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    override fun onItemClicked(item: Task) {
        val intent: Intent = Intent(this, TaskActivity::class.java)
        intent.putExtra("taskID", item.id)
        intent.putExtra("flag", TaskType.EDIT)
        startActivityForResult(intent, OPEN_TASK)
    }

    override fun onStatusChanged(item: Task, status: Boolean) {
        presenter.changeStatus(item.id!!, status)

    }
}
