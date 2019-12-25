package ru.streltsov.todolist.ui.tasklist

import ru.streltsov.todolist.base.BasePresenter
import ru.streltsov.todolist.data.Action
import ru.streltsov.todolist.data.FirebaseRepository
import ru.streltsov.todolist.data.repository.TaskListRepository

class TaskListPresenter : BasePresenter<TaskListView>(), TaskListRepository.TaskListCallback {

    private var db: TaskListRepository = FirebaseRepository(this)

    fun onLoadData() {
        view?.showProgressBar()
        db.getAllTasks()
    }

    fun onChangeStatus(id: String?, boolean: Boolean) {
        when (boolean) {
            true -> db.changeStatus(id!!, boolean)
            false -> db.changeStatus(id!!, boolean)
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