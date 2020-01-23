package ru.streltsov.todolist.ui.tasklist

import ru.streltsov.todolist.ui.base.BasePresenter
import ru.streltsov.todolist.data.Action
import ru.streltsov.todolist.data.repository.TaskListRepositoryImpl
import java.lang.Exception
import javax.inject.Inject

class TaskListPresenter @Inject constructor(private val db:TaskListRepositoryImpl) : BasePresenter<TaskListView>(), TaskListRepositoryImpl.TaskListCallback {

     fun getAllTasks() {
        view?.showProgressBar()
        db.getAllTasks(this)
    }

     fun getTasksByDay() {
         db.getTasksByDay()
    }

     fun changeStatus(id: String, status: Boolean) {
        when (status) {
            true -> db.changeStatus(id, status, this)
            false -> db.changeStatus(id, status,this)
        }
    }

    override fun returnTaskList(data: ArrayList<Task>) {
        view?.initAdapter(data)
        view?.hideProgressBar()
    }

    override fun sendMessage(message: String) {
        view?.showMessage(message)
    }

    override fun updateList(index: Int, action: Action) {
        view?.updateList(index, action)
    }

    override fun addTask(index: Int) {
        view?.addTask(index)
    }

    override fun updateTask(oldIndex: Int, newIndex: Int) {
        view?.updateTask(oldIndex, newIndex)
    }

    override fun deleteTask(index: Int) {
        view?.deleteTask(index)
    }
}