package ru.streltsov.todolist.ui.tasklist

import ru.streltsov.todolist.data.repository.tasklist.TaskListRepositoryImpl
import ru.streltsov.todolist.ui.base.BasePresenter
import javax.inject.Inject

class TaskListPresenter @Inject constructor(private val repository: TaskListRepositoryImpl) : BasePresenter<TaskListView>(), TaskListPresenterImpl.Callback {

  fun getAllTasks() {
    view?.showProgressBar()
    repository.getAllTasks(this)
  }

  fun getTaskList() {
    val list = repository.getTaskList()
    view?.initAdapter(list)
  }

  fun getTasksByDay() {
//         repository.getTasksByDay()
  }

  fun changeStatus(id: String, status: Boolean) {
    when (status) {
      true -> repository.changeStatus(id, status)
      false -> repository.changeStatus(id, status)
    }
  }

  override fun setTaskList(list: ArrayList<Task>) {
    view?.initAdapter(list)
  }
}