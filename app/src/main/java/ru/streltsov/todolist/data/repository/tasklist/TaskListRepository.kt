package ru.streltsov.todolist.data.repository.tasklist

import com.google.firebase.firestore.DocumentSnapshot
import ru.streltsov.todolist.data.provides.tasklit.TaskListProviderImpl
import ru.streltsov.todolist.ui.tasklist.Task
import ru.streltsov.todolist.ui.tasklist.TaskListPresenterImpl
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class TaskListRepository @Inject constructor(private val provider: TaskListProviderImpl) : TaskListRepositoryImpl, TaskListRepositoryImpl.Callback {

  private lateinit var list: ArrayList<Task>
  private lateinit var presenterCallback:TaskListPresenterImpl.Callback

  override fun getAllTasks(callback:TaskListPresenterImpl.Callback) {
    provider.getAllTasks(this)
    presenterCallback = callback
  }

  override fun getTasksByDay(day: Date) {
    provider.getTasksByDay(day, this)
  }

  override fun getTaskById(id: String) {
    provider.getTaskById(id, this)
  }

  override fun changeStatus(id: String, status: Boolean) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun setTaskList(list: List<DocumentSnapshot>) {
    val temp = ArrayList<Task>()
    if (list.isNotEmpty()) {
      for (task in list) {
        temp.add(task.toObject(Task::class.java)!!)
      }
    }
    presenterCallback.setTaskList(temp)
  }

  override fun getTaskList(): ArrayList<Task> {
    return list
  }

  override fun onError(exception: Exception?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

}