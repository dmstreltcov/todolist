package ru.streltsov.todolist.ui.tasklist

import ru.streltsov.todolist.MainActivity
import ru.streltsov.todolist.base.BasePresenter
import ru.streltsov.todolist.data.Action
import ru.streltsov.todolist.data.FirebaseRepository
import ru.streltsov.todolist.data.repository.TaskListRepository
import javax.inject.Inject

class TaskListPresenter : BasePresenter<TaskListView>(), TaskListRepository.TaskListCallback {

    @Inject lateinit var db:TaskListRepository

    init {
        MainActivity.component.inject(this)
    }
     fun getAllTasks() {
        view?.showProgressBar()
        db.getAllTasks(this)
    }

     fun getTasksByDay() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    override fun onSuccess() {
        //TODO - удалить
        // Почему пустой
    }

    override fun onError() {
        //TODO - удалить
        // Почему пустой
    }
}