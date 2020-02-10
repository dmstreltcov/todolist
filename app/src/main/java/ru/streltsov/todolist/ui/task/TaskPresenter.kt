package ru.streltsov.todolist.ui.task

import android.util.Log
import ru.streltsov.todolist.data.provides.TaskProviderImpl
import ru.streltsov.todolist.data.provides.impl.TaskProvider
import ru.streltsov.todolist.ui.base.BasePresenter
import ru.streltsov.todolist.ui.base.Callback
import ru.streltsov.todolist.ui.tasklist.Task
import java.lang.Exception
import java.util.*
import javax.inject.Inject


class TaskPresenter @Inject constructor(private val repository: TaskProvider) : BasePresenter<TaskView>(), Callback.TaskCallback{

    private val TAG: String = "TodoList/Task Presenter"

    fun onSaveTask(task: Task): Boolean {
        if (validate(task)) {
            Log.d("onSaveTask", "Created new task")
            repository.addTask(task, this)
            return true
        }
        return false
    }

    fun getTaskById(id: String) {
        view?.showProgressBar()
        repository.getTaskById(id, this)
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
            repository.deleteTask(id)
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

    override fun returnTask(task: Task) {
        view?.showData(task)
        view?.hideProgressBar()
    }

    override fun onSuccess() {
        Log.d("Task status", "Task created")
    }

    override fun onError(exception: Exception) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

//    override fun sendInfo() {
//        view?.showMessage("Задача удалена")
//    }
}
