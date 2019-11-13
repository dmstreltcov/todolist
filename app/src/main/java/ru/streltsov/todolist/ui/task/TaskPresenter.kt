package ru.streltsov.todolist.ui.task

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import ru.streltsov.todolist.base.BasePresenter
import ru.streltsov.todolist.data.DataBase
import ru.streltsov.todolist.data.FirebaseDB
import ru.streltsov.todolist.ui.tasklist.Task

class TaskPresenter : BasePresenter<TaskView>() {
    private var db: DataBase = FirebaseDB()

    fun onSaveTask(task: Task) :Boolean {
        if(validate(task)){
            db.addTask(task)
            return true
        }
        return false
    }

    fun deleteTask(id:String?) {
        db.deleteTask(id)
        Log.d("Task Presenter", "$id")
    }

    private fun validate(task: Task): Boolean {

        return when {
            task.title.isNullOrEmpty() -> {
                view?.showMessage("Заполните заголовок")
                false
            }
            else -> true
        }
    }

}