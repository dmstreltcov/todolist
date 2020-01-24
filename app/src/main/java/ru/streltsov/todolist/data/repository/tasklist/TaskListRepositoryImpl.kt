package ru.streltsov.todolist.data.repository.tasklist

import com.google.firebase.firestore.DocumentSnapshot
import ru.streltsov.todolist.ui.tasklist.Task
import ru.streltsov.todolist.ui.tasklist.TaskListPresenterImpl
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


interface TaskListRepositoryImpl {

    fun getAllTasks(callback:TaskListPresenterImpl.Callback)
    fun getTasksByDay(day:Date)
    fun getTaskById(id:String)
    fun changeStatus(id: String, status: Boolean)
    fun getTaskList():ArrayList<Task>

    interface Callback {
        fun setTaskList(list: List<DocumentSnapshot>)
        fun onError(exception: Exception?)

    }
}