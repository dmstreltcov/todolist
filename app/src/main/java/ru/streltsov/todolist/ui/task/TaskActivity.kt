package ru.streltsov.todolist.ui.task

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_task.*
import ru.streltsov.todolist.R
import ru.streltsov.todolist.ui.tasklist.Task

class TaskActivity : AppCompatActivity(), TaskView {

    private val TAG:String = "TaskActivity"
    private lateinit var taskTitle:TextView
    private lateinit var taskDescription:TextView
    private lateinit var taskTimestamp:TextView
    private lateinit var editTaskButton:Button
    private lateinit var deleteTaskButton: Button
    private val presenter: TaskPresenter by lazy { TaskPresenter() }

    private var task:Task? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        init()
        task = intent.getParcelableExtra<Task>("task")
        taskTitle.text = task?.title

        editTaskButton.setOnClickListener {
            presenter.editTask()
            showMessage("Задача изменена")
        }

        deleteTaskButton.setOnClickListener {
            presenter.deleteTask(task)
            showMessage("Задача удалена")
            finish()
        }
    }

    private fun init(){
        taskTitle = task_title
        taskDescription = task_description
        taskTimestamp = task_timestamp
        editTaskButton = edit_task_btn
        deleteTaskButton = delete_task_btn
    }

    override fun getContext(): Context = this

    override fun showError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    private fun showMessage(message:String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }
}
