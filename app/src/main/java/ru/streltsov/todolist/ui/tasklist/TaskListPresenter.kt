package ru.streltsov.todolist.ui.tasklist

import ru.streltsov.todolist.data.provides.impl.TaskListProvider
import ru.streltsov.todolist.ui.base.BasePresenter
import ru.streltsov.todolist.ui.base.Callback
import java.lang.Exception
import javax.inject.Inject

class TaskListPresenter @Inject constructor(private val provider: TaskListProvider) : BasePresenter<TaskListView>(), Callback.TaskListCallback {

  fun getAllTasks() {
    view?.showProgressBar()
    provider.getAllTasks(this)
  }

  fun getTaskList() {
//    val list = repository.getTaskList()
//    view?.initAdapter(list)
  }

  fun getTasksByDay() {
//         repository.getTasksByDay()
  }

  fun changeStatus(id: String, status: Boolean) {
//    when (status) {
//      true -> repository.changeStatus(id, status)
//      false -> repository.changeStatus(id, status)
//    }
  }

  fun setTaskList(list: ArrayList<Task>) {
//    view?.initAdapter(list)
  }

  override fun returnList(list: ArrayList<Task>) {
    view?.initAdapter(list)
  }

  override fun onSuccess() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun onError(exception: Exception) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}