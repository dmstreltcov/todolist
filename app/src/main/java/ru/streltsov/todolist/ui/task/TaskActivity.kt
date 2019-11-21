package ru.streltsov.todolist.ui.task

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
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
    private lateinit var timeStart: TextInputEditText
    private lateinit var saveTaskButton: Button
    private val presenter: TaskPresenter by lazy { TaskPresenter() }
    private lateinit var dateStartSetListener: DatePickerDialog.OnDateSetListener
    private lateinit var timeStartSetListener: TimePickerDialog.OnTimeSetListener

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
            Log.d(TAG, "${task.dateStart}")
            val aa = formatDate(task.dateStart).split("|")
            for (str:String in aa){
                print(str)
            }

            dateStart.setText(formatDate(task.dateStart).split("|")[1])
            timeStart.setText(formatDate(task.dateStart).split("|")[0])

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

        dateStart.setOnClickListener {
            presenter.onDateStartClicked()
        }

        timeStart.setOnClickListener {
            presenter.onTimeStartClicked()
        }
    }

    private fun getTaskData(): Task {
        return Task(
            title = taskTitle.text.toString(),
            description = taskDescription.text.toString(),
            createDate = Timestamp.now(),
            dateStart = parseDate(
                time_start_input.text.toString(),
                start_date_input.text.toString()
            )
        )
    }

    private fun init() {
        taskTitle = task_title
        taskDescription = task_description
        deleteTaskButton = delete_task_btn
        saveTaskButton = save_task_btn
        dateStart = start_date_input
        timeStart = time_start_input
        dateStartSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            presenter.onDateStartSet(
                year,
                month,
                day
            )
        }
        timeStartSetListener = TimePickerDialog.OnTimeSetListener { picker, hour, minute ->
            presenter.onTimeStartSet(
                hour,
                minute
            )
        }
    }


    private fun formatDate(dateStart: Timestamp?): String {
        return try {
            val format = SimpleDateFormat("HH:mm|dd.MM.yyyy")
            val date = Date(dateStart!!.seconds * 1000)
            format.format(date)
        } catch (e: Exception) {
            e.toString()
        }
    }

    private fun parseDate(time: String, date: String): Timestamp {
        val format = SimpleDateFormat("HH:mm dd.MM.yyyy")
        val data = format.parse("$time $date")
        val timestamp: Timestamp = Timestamp(data)
        return timestamp


    }

    override fun setStartDateText(date: String) {
        dateStart.text = Editable.Factory.getInstance().newEditable(date)

    }

    override fun setStartTimeText(time: String) {
        timeStart.text = Editable.Factory.getInstance().newEditable(time)

    }

    override fun displayDatePickerDialog(year: Int, month: Int, day: Int) {
        val dialog: DatePickerDialog = DatePickerDialog(
            this,
            R.style.Theme_MaterialComponents_Dialog_MinWidth,
            dateStartSetListener,
            year,
            month,
            day
        )
        dialog.window?.setBackgroundDrawable(
            GradientDrawable(
                GradientDrawable.Orientation.BL_TR,
                getContext().resources.getIntArray(R.array.gradient)
            )
        )
        dialog.show()
    }

    override fun displayTimePickerDialog(hour: Int, minute: Int) {
        val dialog: TimePickerDialog = TimePickerDialog(
            this,
            R.style.Theme_MaterialComponents_Dialog_MinWidth,
            TimePickerDialog.OnTimeSetListener() { picker, hour, minute ->
                presenter.onTimeStartSet(hour, minute)
            },
            hour,
            minute,
            true
        )
        dialog.show()
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
