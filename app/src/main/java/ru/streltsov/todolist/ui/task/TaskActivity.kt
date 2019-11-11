package ru.streltsov.todolist.ui.task

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_task.*
import ru.streltsov.todolist.R
import ru.streltsov.todolist.ui.tasklist.Task

class TaskActivity : AppCompatActivity(), TaskView {

    private lateinit var taskTitle:TextView
    private lateinit var taskDescription:TextView
    private lateinit var taskTimestamp:TextView
    private var task:Task? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        init()
        task = intent.getParcelableExtra<Task>("task")
        taskTitle.text = task?.title
    }

    private fun init(){
        taskTitle = task_title
        taskDescription = task_description
        taskTimestamp = task_timestamp
    }

    override fun getContext(): Context = this

    override fun showError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
