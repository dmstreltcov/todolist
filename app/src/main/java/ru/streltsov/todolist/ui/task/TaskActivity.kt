package ru.streltsov.todolist.ui.task

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Timestamp
import kotlinx.android.synthetic.main.activity_task.*
import ru.streltsov.todolist.R
import ru.streltsov.todolist.ui.tasklist.Task
import java.lang.Exception
import java.sql.Date
import java.text.SimpleDateFormat

class TaskActivity : AppCompatActivity(), TaskView {

    private val TAG: String = "TODO _TaskActivity"
    private lateinit var taskTitle: EditText
    private lateinit var taskDescription: EditText
    private lateinit var taskTimestamp: TextView
    private lateinit var deleteTaskButton: Button
    private lateinit var saveTaskButton: Button
    private val presenter: TaskPresenter by lazy { TaskPresenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        init()
        val task = intent.getParcelableExtra<Task>("task")
        Log.d(TAG, "$task")



        if (task != null) {
            taskTitle.setText(task.title)
            taskDescription.setText(task.description)
            taskTimestamp.text = formatDate(task.createDate)

            saveTaskButton.visibility = View.GONE
            deleteTaskButton.setOnClickListener {
                presenter.deleteTask(task.createDate)
                showMessage("Задача удалена")
                finish()
            }
        }else{
            deleteTaskButton.visibility = View.GONE
            saveTaskButton.setOnClickListener {
                presenter.onSaveTask(getTaskData())
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

    private fun getTaskData(): Task {
        return Task(
            taskTitle.text.toString(),
            taskDescription.text.toString(),
            createDate = Timestamp.now()
        )
    }

    private fun init() {
        taskTitle = task_title
        taskDescription = task_description
        taskTimestamp = task_create_date
        deleteTaskButton = delete_task_btn
        saveTaskButton = save_task_btn
    }

    private fun formatDate(createDate: Timestamp?): String {
        try {
            val format = SimpleDateFormat("dd/MM/yyyy HH:mm")
            val date = Date(createDate!!.seconds * 1000)
            println("date is $date")
            return format.format(date)
        } catch (e: Exception) {
            return e.toString()
        }
    }

    override fun getContext(): Context = this

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
