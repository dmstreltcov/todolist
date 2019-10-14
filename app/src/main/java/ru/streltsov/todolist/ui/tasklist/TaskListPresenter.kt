package ru.streltsov.todolist.ui.tasklist

import android.os.Parcelable
import com.google.firebase.auth.FirebaseUser
import ru.streltsov.todolist.data.DataBase
import ru.streltsov.todolist.data.FirebaseDB

class TaskListPresenter(private val _view : TaskListView) : ITaskListPresenter<TaskListView>() {

    private val TAG: String = "TaskListPresenter"
    private var db: DataBase = FirebaseDB()

    override fun onLoadData(currentUser: Parcelable) {

    }
}