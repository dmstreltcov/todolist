package ru.streltsov.todolist.ui.task

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
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
    private lateinit var deleteTaskButton: Button
    private lateinit var dateStart: TextInputEditText
    private lateinit var dateEnd: TextInputEditText
    private lateinit var remind: CheckBox
    private lateinit var saveTaskButton: Button
    private val presenter: TaskPresenter by lazy { TaskPresenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        presenter.attach(this)
        init()
        val task = intent.getParcelableExtra<Task>("task")
        Log.d(TAG, "$task")



        if (task != null) {
            taskTitle.setText(task.title)
            taskDescription.setText(task.description)
            when (task.remind) {
                0L -> remind.isChecked = false
                1L -> remind.isChecked = true
            }
            dateStart.setText(formatDate(task.createDate))
            dateEnd.setText(formatDate(task.dateEnd))

            saveTaskButton.visibility = View.GONE
            deleteTaskButton.setOnClickListener {
                presenter.deleteTask(task.id)
                showMessage("Задача удалена")
                finish()
            }
        } else {
            deleteTaskButton.visibility = View.GONE
            saveTaskButton.setOnClickListener {
                if (presenter.onSaveTask(getTaskData())) {
                    setResult(Activity.RESULT_OK)
                    finish()
                }

            }
        }
    }

    private fun getTaskData(): Task {
        return Task(
            title = taskTitle.text.toString(),
            description = taskDescription.text.toString(),
            createDate = Timestamp.now(),
            remind = if (remind_check_box.isChecked) 0L else 1L

        )
    }

    private fun init() {
        taskTitle = task_title
        taskDescription = task_description
        deleteTaskButton = delete_task_btn
        saveTaskButton = save_task_btn
        dateStart = start_date_input
        dateEnd = end_date_input
        remind = remind_check_box
    }

    private fun formatDate(createDate: Timestamp?): String {
        try {
            val format = SimpleDateFormat("HH:mm, dd.MM.yyyy")
            val date = Date(createDate!!.seconds * 1000)
            return format.format(date)
        } catch (e: Exception) {
            return e.toString()
        }
    }

    override fun getContext(): Context = this

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }
}
