package ru.streltsov.todolist.data.repository.task

import ru.streltsov.todolist.data.provides.task.TaskProviderImpl
import ru.streltsov.todolist.ui.tasklist.Task
import javax.inject.Inject

class TaskRepository @Inject constructor(private val provider: TaskProviderImpl) : TaskRepositoryImpl, TaskRepositoryImpl.TaskCallback {

    override fun sendInfo() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun returnTask(task: Task) {

    }

    override fun addTask(task: Task, callback: TaskRepositoryImpl.TaskCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteTask(id: String, callback: TaskRepositoryImpl.TaskCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTaskById(id: String, callback: TaskRepositoryImpl.TaskCallback) {

    }
}