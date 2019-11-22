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
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
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
    private val TYPE_TASK: Int = 0
    private val TYPE_EDIT: Int = 1
    private lateinit var taskTitle: EditText
    private lateinit var taskDescription: EditText
    private lateinit var dateStart: TextInputEditText
    private lateinit var timeStart: TextInputEditText
    private val presenter: TaskPresenter by lazy { TaskPresenter() }
    private lateinit var dateStartSetListener: DatePickerDialog.OnDateSetListener
    private lateinit var timeStartSetListener: TimePickerDialog.OnTimeSetListener
    private lateinit var itemDelete: MenuItem
    private var flag: Int = 0
    private var taskId: String? = null
    private lateinit var actionBarToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        presenter.attach(this)
        init()
        val task = intent.getParcelableExtra<Task>("task")
        Log.d(TAG, "$task")

        if (task != null) {
            flag = TYPE_TASK
            taskId = task.id
            taskTitle.setText(task.title)
            taskDescription.setText(task.description)
            Log.d(TAG, "${task.dateStart}")
            val aa = formatDate(task.dateStart).split("|")
            for (str: String in aa) {
                print(str)
            }

            dateStart.setText(formatDate(task.dateStart).split("|")[1])
            timeStart.setText(formatDate(task.dateStart).split("|")[0])

        } else {
            flag = TYPE_EDIT
        }

        dateStart.setOnClickListener {
            presenter.onDateStartClicked()
        }

        timeStart.setOnClickListener {
            presenter.onTimeStartClicked()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_edit -> {
                actionBarToolbar.menu.findItem(R.id.action_edit).isVisible = false
                actionBarToolbar.menu.findItem(R.id.action_delete).isVisible = false
                actionBarToolbar.menu.findItem(R.id.action_save).isVisible = true
            }
            R.id.action_save -> {
                if (presenter.onSaveTask(getTaskData())) {
                    setResult(Activity.RESULT_OK)
                    finish()
                }

            }
            R.id.action_delete -> {
                presenter.deleteTask(taskId)
                showMessage("Задача удалена")
                finish()
            }
        }
        return true
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activiy_main, menu)
        val action_delete = menu?.findItem(R.id.action_delete)
        val action_save = menu?.findItem(R.id.action_save)
        val action_edit = menu?.findItem(R.id.action_edit)
        when (flag) {
            TYPE_EDIT -> {
                action_delete?.isVisible = false
                action_edit?.isVisible = false
                action_save?.isVisible = true
            }
            TYPE_TASK -> {
                action_delete?.isVisible = true
                action_edit?.isVisible = true
                action_save?.isVisible = false
            }
        }

        return true
    }


    private fun init() {
        taskTitle = task_title
        taskDescription = task_description
        dateStart = start_date_input
        timeStart = time_start_input
        actionBarToolbar = findViewById(R.id.toolbar_action_bar)
        setSupportActionBar(actionBarToolbar)
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
