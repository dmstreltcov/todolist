package ru.streltsov.todolist.ui.tasklist

import ru.streltsov.todolist.base.BasePresenter
import ru.streltsov.todolist.data.repository.DatabaseRepository
import ru.streltsov.todolist.data.FirebaseRepository
import ru.streltsov.todolist.data.repository.TaskListRepository

class TaskListPresenter : BasePresenter<TaskListView>(), TaskListRepository.TaskListCallback {

    private var db: TaskListRepository = FirebaseRepository()

    fun onLoadData() {
        view?.showProgressBar()
        db.setCallback(this)
        db.getAllTasks()
    }

    fun onChangeStatus(id: String?, boolean: Boolean) {
        db.setCallback(this)
        when (boolean) {
            true -> db.changeStatus(id!!, boolean)
            false -> db.changeStatus(id!!, boolean)
        }
    }

    override fun returnTask(task: Task) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun returnTaskList(data: ArrayList<Task>) {
        view?.initAdapter(data)
        view?.hideProgressBar()
    }

    override fun sendMessage(message: String) {
        view?.showMessage(message)
    }

    override fun onSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}