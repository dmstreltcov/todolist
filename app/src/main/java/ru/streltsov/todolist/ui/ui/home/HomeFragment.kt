package ru.streltsov.todolist.ui.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_task_list.*
import kotlinx.android.synthetic.main.activity_task_list.toolbar
import kotlinx.android.synthetic.main.app_bar_drawler_menu.*
import ru.streltsov.todolist.App
import ru.streltsov.todolist.R
import ru.streltsov.todolist.data.Action
import ru.streltsov.todolist.ui.task.TaskActivity
import ru.streltsov.todolist.ui.task.TaskType
import ru.streltsov.todolist.ui.tasklist.*
import ru.streltsov.todolist.ui.utils.ItemsDiffUtils
import javax.inject.Inject

class HomeFragment : Fragment(), TaskListView, TaskListAdapter.Callback {
    private val OPEN_TASK = 1002
    private lateinit var linearLayout: LinearLayoutManager
    //TODO зависимость
    private lateinit var adapter: TaskListAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var addTaskBtn: FloatingActionButton



    private lateinit var list: List<Item>
    private lateinit var uid: String
    @Inject
    lateinit var presenter: TaskListPresenter


    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {

        App.instance.getTaskListComponent().inject(this)
        presenter.attach(this)



        presenter.getAllTasks()
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = root.findViewById(R.id.tasklist)
//        private lateinit var actionBarToolbar: Toolbar
//        addTaskBtn = root.findViewById(R.id.add_task_button)
//        actionBarToolbar = root.findViewById(R.id.toolbar)
        linearLayout = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayout
//        progressBar = progressBarList
//        activity.setActionBar(actionBarToolbar)

        return root
    }

    override fun initAdapter(taskList: ArrayList<Task>) {
        adapter = TaskListAdapter()
        adapter.setData(taskList)
        adapter.setCallback(this)
        recyclerView.adapter = adapter
    }

    override fun updateList(index: Int, action: Action) {
        when (action) {
            Action.REMOVED -> adapter.notifyItemRemoved(index)
            Action.MODIFIED -> adapter.notifyItemChanged(index)
            Action.ADDED -> adapter.notifyItemInserted(index)
        }
    }

    override fun addTask(index: Int) {
        adapter.notifyItemInserted(index)
    }

    override fun updateTask(oldIndex: Int, newIndex: Int) {
        if (oldIndex != newIndex) {
            adapter.notifyItemMoved(oldIndex, newIndex)
        }
        adapter.notifyItemChanged(newIndex)
    }

    override fun deleteTask(index: Int) {
        val itemsDiffUtils = ItemsDiffUtils(adapter.getData(), list)
        val diffResult = DiffUtil.calculateDiff(itemsDiffUtils)
        diffResult.dispatchUpdatesTo(adapter)
    }


    override fun showMessage(message: String) {
    }

    override fun showProgressBar() {

    }

    override fun hideProgressBar() {
    }

    override fun getContext(): Context = layoutInflater.context

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    override fun onItemClicked(item: Task) {
        val intent = Intent(activity, TaskActivity::class.java)
        intent.putExtra("taskID", item.id)
        intent.putExtra("flag", TaskType.EDIT)
        startActivityForResult(intent, OPEN_TASK)
    }

    override fun onStatusChanged(item: Task, status: Boolean, position: Int) {
        presenter.changeStatus(item.id!!, status)

    }


}