package ru.streltsov.todolist.ui.drawler.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.streltsov.todolist.App
import ru.streltsov.todolist.R
import ru.streltsov.todolist.data.Action
import ru.streltsov.todolist.ui.task.TaskActivity
import ru.streltsov.todolist.ui.task.TaskType
import ru.streltsov.todolist.ui.tasklist.*
import ru.streltsov.todolist.ui.utils.TaskListUtils
import javax.inject.Inject

class TaskListFragment : Fragment(), TaskListView, TaskListAdapter.Callback {

    private val OPEN_TASK = 1002
    private lateinit var linearLayout: LinearLayoutManager
    //TODO зависимость
    private lateinit var adapter: TaskListAdapter
    private lateinit var recyclerView: RecyclerView
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
        linearLayout = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayout
        return root
    }

    override fun initAdapter(taskList: ArrayList<Task>) {

        adapter = TaskListAdapter()
        val util = TaskListUtils()

        adapter.setData(util.createTodayTaskList(taskList))
        adapter.setCallback(this)
        recyclerView.adapter = adapter
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
//        val itemsDiffUtils = ItemsDiffUtils(adapter.getData(), list)
//        val diffResult = DiffUtil.calculateDiff(itemsDiffUtils)
//        diffResult.dispatchUpdatesTo(adapter)
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