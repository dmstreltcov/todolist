package ru.streltsov.todolist.ui.task

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

    private val TAG: String = "TaskActivity"
    private lateinit var taskTitle: EditText
    private lateinit var taskDescription: EditText
    private lateinit var taskTimestamp: TextView
    private lateinit var editTaskButton: Button
    private lateinit var deleteTaskButton: Button
    private lateinit var saveTaskButton: Button
    private val presenter: TaskPresenter by lazy { TaskPresenter() }

    private var task: Task? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        init()
        task = intent.getParcelableExtra<Task>("task") as Task
        Log.d(TAG, task!!.createDate.toString())
        taskTitle.setText(task?.title)
        taskDescription.setText(task?.description)
        taskTimestamp.text = formatDate(task?.createDate)

        editTaskButton.setOnClickListener {
            presenter.editTask()
            showMessage("Задача изменена")
        }

        deleteTaskButton.setOnClickListener {
            presenter.deleteTask(task!!.createDate)
            showMessage("Задача удалена")
            finish()
        }
        saveTaskButton.setOnClickListener {
            presenter.onSaveTask(getTask())
            Log.d(TAG, "SAVE CLICKED")
        }
    }

    private fun getTask():Task{
        return Task(
            taskTitle.text.toString(),
            taskDescription.text.toString()
        )
    }

    private fun init() {
        taskTitle = task_title
        taskDescription = task_description
        taskTimestamp = task_timestamp
        editTaskButton = edit_task_btn
        deleteTaskButton = delete_task_btn
        saveTaskButton = save_task_btn
    }

    private fun formatDate(timestamp:Timestamp?) : String{
        try{
            val format = SimpleDateFormat("dd/MM/yyyy")
            val date = Date(timestamp!!.seconds*1000)
            println("date is $date")
            return format.format(date)
        }catch (e:Exception){
           return e.toString()
        }
    }

    override fun getContext(): Context = this

    override fun showError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
