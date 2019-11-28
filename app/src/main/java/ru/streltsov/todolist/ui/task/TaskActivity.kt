package ru.streltsov.todolist.ui.task

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Timestamp
import kotlinx.android.synthetic.main.activity_task.*
import ru.streltsov.todolist.R
import ru.streltsov.todolist.alarm.AlarmReceiver
import ru.streltsov.todolist.alarm.NotificationHelper
import ru.streltsov.todolist.ui.tasklist.Task
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class TaskActivity : AppCompatActivity(), TaskView {

    private val TAG: String = "TaskActivity"
    private lateinit var taskTitle: EditText
    private lateinit var taskDescription: EditText
    private lateinit var dateStart: TextInputEditText
    private lateinit var timeStart: TextInputEditText
    private val presenter: TaskPresenter by lazy { TaskPresenter() }
    private lateinit var dateStartSetListener: DatePickerDialog.OnDateSetListener
    private lateinit var timeStartSetListener: TimePickerDialog.OnTimeSetListener
    private lateinit var flag: TaskType
    private var taskId: String? = null
    private lateinit var actionBarToolbar: Toolbar
    private lateinit var  task: Task
    private lateinit var alarmManager: AlarmManager
    private var timeAlarm:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        presenter.attach(this)
        init()


        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val task = intent.getParcelableExtra<Task>("task")
        if (task != null) {
            flag = TaskType.EDIT
            taskId = task.id
            taskTitle.setText(task.title)
            taskDescription.setText(task.description)
            dateStart.setText(formatDate(task.dateStart).split("|")[1])
            timeStart.setText(formatDate(task.dateStart).split("|")[0])
        } else {
            flag = TaskType.NEW
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
                task = getTaskData()
                if (presenter.onSaveTask(task)) {
                    setResult(Activity.RESULT_OK)

                    if(task.dateStart != null) setAlarm(task)

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

    private fun setAlarm(task:Task){
        val intent = Intent(this, AlarmReceiver::class.java)
        intent.putExtra("title", task.title)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeAlarm, pendingIntent)
    }

    private fun getTaskData(): Task {
        return Task(
            id = taskId,
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
        val actionDelete = menu?.findItem(R.id.action_delete)
        val actionSave = menu?.findItem(R.id.action_save)
        val actionEdit = menu?.findItem(R.id.action_edit)

        when (flag) {
            TaskType.NEW -> {
                actionDelete?.isVisible = false
                actionEdit?.isVisible = false
                actionSave?.isVisible = true
            }
            TaskType.EDIT -> {
                actionDelete?.isVisible = true
                actionEdit?.isVisible = true
                actionSave?.isVisible = false
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
        val data: Date = format.parse("$time $date")
        timeAlarm = data.time
        return Timestamp(data)


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
