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

class TaskPresenter : BasePresenter<TaskView>(){
    private var db: DataBase = FirebaseDB()

    fun editTask(){

    }

    fun onSaveTask(task:Task){
        db.addTask(task)
    }

    fun deleteTask(createDate: Timestamp?){
        db.deleteTask(createDate)
        Log.d("Task Presenter", "$createDate")
    }

}