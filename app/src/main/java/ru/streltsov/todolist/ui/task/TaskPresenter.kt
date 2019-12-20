package ru.streltsov.todolist.ui.task

import android.util.Log
import ru.streltsov.todolist.base.BasePresenter
import ru.streltsov.todolist.data.repository.DatabaseRepository
import ru.streltsov.todolist.data.FirebaseRepository
import ru.streltsov.todolist.data.repository.Callback
import ru.streltsov.todolist.data.repository.TaskRepository
import ru.streltsov.todolist.ui.tasklist.Task
import java.util.*
import kotlin.collections.ArrayList


class TaskPresenter : BasePresenter<TaskView>(), TaskRepository.TaskCallback {
    private var db: DatabaseRepository = FirebaseRepository()

    private val TAG: String = "TodoList/Task Presenter"

    fun onSaveTask(task: Task): Boolean {
        if (validate(task)) {
            Log.d("onSaveTask", "Created new task")
            db.setCallback(this)
            db.addTask(task)
            return true
        }
        return false
    }

    fun getTaskById(id: String) {
        db.setCallback(this)
        view?.showProgressBar()
        db.getTaskById(id)
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

    fun deleteTask(id: String) {

        try {
            db.deleteTask(id)
        } catch (e: NullPointerException) {
            view?.showMessage("Не удалось удалить задачу")
            e.printStackTrace()
        }
        Log.d("Task Presenter", id)
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

//    override fun returnData(data: ArrayList<Task>) {
//        view?.hideProgressBar()
//        if (data.size > 0) {
//            view?.showData(data[0])
//        }
//    }

    override fun sendInfo() {
        view?.showMessage("Задача удалена")
    }

    override fun onSuccess() {
        view?.showMessage("Задача сохранена")
    }

    override fun onError() {
        view?.showMessage("Возникла ошибка. Попробуйте снова")
    }


}
