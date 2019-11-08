package ru.streltsov.todolist.ui.tasklist

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseUser
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.tasklist_item.*
import ru.streltsov.todolist.R
import kotlin.math.log
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import ru.streltsov.todolist.ui.tasklist.TaskListActivity




class TaskListActivity : AppCompatActivity(), TaskListView {


    private var currentUser: FirebaseUser? = null

    private val TAG: String = "TaskListActivity"
    private val presenter: TaskListPresenter by lazy { TaskListPresenter() }
    private lateinit var adapter : FirestoreRecyclerAdapter<Task, TaskViewHolder>
    private lateinit var recyclerView:RecyclerView
//    private lateinit var query:Query
    private lateinit var db:FirebaseFirestore
    lateinit var linearLayout: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "OnCreate()")
        setContentView(R.layout.activity_task_list)
        currentUser = intent?.getParcelableExtra("user") // <- вот тут мне кажется чепуха
        recyclerView = findViewById(R.id.tasklist)
        linearLayout = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayout
        db = FirebaseFirestore.getInstance()
//        loadData()
        getTasks()
    }

    override fun loadData() {
//        query = presenter.onLoadData()
    }

    fun getTasks(){
        val query:Query = db.collection("tasks")
        val options = FirestoreRecyclerOptions.Builder<Task>()
            .setQuery(query, Task::class.java)
            .build()

        adapter = object : FirestoreRecyclerAdapter<Task, TaskViewHolder>(options) {
            override fun onBindViewHolder(
                taskViewHolder: TaskViewHolder,
                i: Int,
                task: Task
            ) {
                taskViewHolder.setData(task.title, task.description)
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.tasklist_item, parent, false)
                return TaskViewHolder(view)
            }
        }
        adapter.notifyDataSetChanged()
        recyclerView.adapter = adapter
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


    private inner class TaskViewHolder (private val view: View) :
        RecyclerView.ViewHolder(view) {



        fun setData(title:String, description:String) {
            Log.d(TAG, "title: $title description: $description")
            list_item_title.text = title
            list_item_description.text = description
        }
    }

}
