package ru.streltsov.todolist.ui.tasklist

import android.os.Parcelable
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.Query
import ru.streltsov.todolist.base.BasePresenter
import ru.streltsov.todolist.data.DataBase
import ru.streltsov.todolist.data.FirebaseDB

class TaskListPresenter : BasePresenter<TaskListView>() {

    private var db: DataBase = FirebaseDB()

    var options:FirestoreRecyclerOptions<Task> = FirestoreRecyclerOptions.Builder<Task>().setQuery(db.getData(), Task::class.java).build()

    fun onLoadData() : Query{
        return db.getData()
    }
}