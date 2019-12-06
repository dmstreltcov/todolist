package ru.streltsov.todolist.ui.task

import android.util.Log
import ru.streltsov.todolist.base.BasePresenter
import ru.streltsov.todolist.data.DataBase
import ru.streltsov.todolist.data.FirebaseDB
import ru.streltsov.todolist.ui.tasklist.Task
import java.util.*
import kotlin.math.min

class TaskPresenter : BasePresenter<TaskView>(), DataBase.Callback {
    private var db: DataBase = FirebaseDB()



    fun onSaveTask(task: Task): Boolean {
        if (validate(task)) {
            return if (task.id == null) {
                Log.d("onSaveTask", "Created new task")
                db.addTask(task)
                true
            } else {
                db.updateTask(task)
                true
            }
        }
        return false
    }

    fun onDateStartClicked() {
        showCurrentDate()
    }

    fun onTimeStartClicked() {
        showCurrentTime()
    }

    private fun showCurrentDate() {
        val calendar: Calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

        view?.displayDatePickerDialog(year, month, day);
    }

    private fun showCurrentTime() {
        val calendar: Calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY);
        val minute = calendar.get(Calendar.MINUTE);
        view?.displayTimePickerDialog(hour, minute)
    }

    fun onDateStartSet(year: Int, month: Int, day: Int) {
        val _month = month + 1
        val date: String = "$day.$_month.$year "
        view?.setStartDateText(date)
    }

    fun onTimeStartSet(hour: Int, minute: Int) {
        val time: String = "$hour:$minute"
        view?.setStartTimeText(time)
    }

    fun deleteTask(id: String?) {
        try {
            db.deleteTask(id)
        } catch (e: NullPointerException) {
            view?.showMessage("Не удалось удалить задачу")
            e.printStackTrace()
        }
        Log.d("Task Presenter", "$id")
    }

    private fun validate(task: Task): Boolean {
        return when {
            task.title.isNullOrEmpty() -> {
                view?.showMessage("Заполните заголовок")
                false
            }
            else -> true
        }
    }

    fun getTaskById(id:String){
        db.setCallback(this)
        db.getTaskByID(id)
    }

    override fun returnData(task: Task?) {
        if (task != null)
        view?.showData(task)
    }


}
