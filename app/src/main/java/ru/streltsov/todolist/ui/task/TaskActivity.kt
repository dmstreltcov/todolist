package ru.streltsov.todolist.ui.task

import android.app.*
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Timestamp
import kotlinx.android.synthetic.main.activity_task.*
import ru.streltsov.todolist.App
import ru.streltsov.todolist.MainActivity
import ru.streltsov.todolist.R
import ru.streltsov.todolist.ui.alarm.AlarmReceiver
import ru.streltsov.todolist.ui.alarm.BootCompleteReceiver
import ru.streltsov.todolist.ui.tasklist.Task
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class TaskActivity : AppCompatActivity(), TaskView {

    private val TAG: String = "TodoList/TaskActivity"
    private val TASK_SAVED: Int = 1412
    private val TASK_DELETED: Int = 1413

    private lateinit var taskTitle: EditText
    private lateinit var taskDescription: EditText
    private lateinit var dateStart: TextInputEditText
    private lateinit var timeStart: TextInputEditText
    private lateinit var fab: FloatingActionButton
    private lateinit var progressBar: ProgressBar
    private lateinit var actionBarToolbar: BottomAppBar
    private lateinit var dateStartSetListener: DatePickerDialog.OnDateSetListener
    private lateinit var timeStartSetListener: TimePickerDialog.OnTimeSetListener
    @Inject lateinit var presenter: TaskPresenter

    private lateinit var flag: TaskType
    private var taskId: String = ""

    //TODO зависимость
    private lateinit var alarmManager: AlarmManager
    private var timeAlarm: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        App.instance.getAppComponent().inject(this)
        presenter.attach(this)
        flag = intent.extras!!["flag"] as TaskType
        initElements()
        setListeners()
        setSupportActionBar(actionBarToolbar)
        setAlarmManager()

        if (flag == TaskType.EDIT) {
            taskId = intent.getStringExtra("taskID")!!
            presenter.getTaskById(taskId)
        } else {
            fab.setImageDrawable(resources.getDrawable(R.drawable.ic_save, getContext().theme))
        }
    }



    private fun initElements() {
        taskTitle = task_title
        taskDescription = task_description
        dateStart = start_date_input
        timeStart = time_start_input
        fab = findViewById(R.id.fab)
        progressBar = progressBarTask
        actionBarToolbar = findViewById(R.id.taskBottomAppBar)
    }

    private fun setListeners() {
        fab.setOnClickListener {
            when (flag) {
                TaskType.EDIT -> {
                    onEditTask()
                }
                TaskType.NEW -> {
                    onSaveTask()
                }
            }
        }

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

        dateStart.setOnClickListener {
            presenter.onDateStartClicked()
        }

        timeStart.setOnClickListener {
            presenter.onTimeStartClicked()
        }
    }

    private fun onEditTask() {
        fab.setImageDrawable(
            resources.getDrawable(
                R.drawable.ic_save,
                getContext().theme
            )
        )
        flag = TaskType.NEW
        changeInputEnableProperty(flag)
    }

    private fun onSaveTask() {
        val task = getTaskData()
        if (presenter.onSaveTask(task)) {
            setResult(TASK_SAVED)
            if (task.dateStart != null && (System.currentTimeMillis() < timeAlarm) && !task.status)
                setAlarm(task)
            finish()
        }
    }

    private fun setAlarmManager() {
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val receiver = ComponentName(applicationContext, BootCompleteReceiver::class.java)
        applicationContext.packageManager?.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    override fun showData(task: Task) {
        flag = TaskType.EDIT
        taskTitle.setText(task.title)
        taskDescription.setText(task.description)
        if (task.dateStart != null) {
            dateStart.setText(formatDate(task.dateStart).split("|")[1])
            timeStart.setText(formatDate(task.dateStart).split("|")[0])
        }
        fab.setImageDrawable(resources.getDrawable(R.drawable.ic_edit, getContext().theme))
        changeInputEnableProperty(flag)
    }

    private fun changeInputEnableProperty(flag: TaskType) {
        when (flag) {
            TaskType.NEW -> {
                taskTitle.isEnabled = true
                taskDescription.isEnabled = true
                dateStart.isEnabled = true
                timeStart.isEnabled = true
            }
            TaskType.EDIT -> {
                taskTitle.isEnabled = false
                taskDescription.isEnabled = false
                dateStart.isEnabled = false
                timeStart.isEnabled = false
            }
        }
    }

    private fun setAlarm(task: Task) {
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeAlarm, createPendingIntent(task))
    }

    private fun createPendingIntent(task: Task): PendingIntent {
        val intent = createIntent(task)
        return PendingIntent.getBroadcast(
            this,
            getTaskRequestCode(task),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun createIntent(task: Task): Intent {
        val intent = Intent(this, AlarmReceiver::class.java)
        intent.putExtra("title", task.title)
        intent.putExtra("id", task.id)
        return intent
    }

    private fun getTaskRequestCode(task:Task):Int{
        return task.id.hashCode()
    }

    private fun cancelAlarm() {
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, taskId.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.cancel(pendingIntent)
    }

    private fun getTaskData(): Task {
        return Task(
            id = setTaskId(),
            title = taskTitle.text.toString(),
            description = taskDescription.text.toString(),
            createDate = Timestamp.now(),
            dateStart = parseDate(
                time_start_input.text.toString(),
                start_date_input.text.toString()
            )
        )
    }

    private fun setTaskId(): String? {
        return if (taskId.isEmpty()) {
            UUID.randomUUID().toString()
        } else {
            taskId
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

    private fun parseDate(time: String?, date: String?): Timestamp? {
        if (!time.isNullOrEmpty() && !date.isNullOrEmpty()) {
            val format = SimpleDateFormat("HH:mm dd.MM.yyyy")
            val data: Date = format.parse("$time $date")
            timeAlarm = data.time
            return Timestamp(data)
        }
        return null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activiy_main, menu)
        val actionDelete = menu?.findItem(R.id.action_delete)
        val actionCancel = menu?.findItem(R.id.action_cancel)

        when (flag) {
            TaskType.NEW -> {
                actionDelete?.isVisible = false
                actionCancel?.isVisible = false
            }
            TaskType.EDIT -> {
                actionDelete?.isVisible = true
                actionCancel?.isVisible = true
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> {
                presenter.deleteTask(taskId)
                setResult(TASK_DELETED)
                cancelAlarm()
                finish()
            }
        }

        return true
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
            R.style.DatePicker,
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
            R.style.TimePicker,
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

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE

        taskTitle.visibility = View.GONE
        taskDescription.visibility = View.GONE
        dateStart.visibility = View.GONE
        timeStart.visibility = View.GONE
        fab.hide()
        actionBarToolbar.visibility = View.GONE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE

        taskTitle.visibility = View.VISIBLE
        taskDescription.visibility = View.VISIBLE
        dateStart.visibility = View.VISIBLE
        timeStart.visibility = View.VISIBLE
        fab.show()
        actionBarToolbar.visibility = View.VISIBLE
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }
}
