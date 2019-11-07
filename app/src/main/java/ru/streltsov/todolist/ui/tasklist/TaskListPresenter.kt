package ru.streltsov.todolist.ui.tasklist

import android.os.Parcelable
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseUser
import ru.streltsov.todolist.base.BasePresenter
import ru.streltsov.todolist.data.DataBase
import ru.streltsov.todolist.data.FirebaseDB

class TaskListPresenter : BasePresenter<TaskListView>() {


    //ХЗ КАКОЕ-ТО SHIT

    private var db: DataBase = FirebaseDB()

    var options:FirestoreRecyclerOptions<Task> = FirestoreRecyclerOptions.Builder<Task>().setQuery(db.getData(), Task::class.java).build()

    var adapter:FirestoreRecyclerAdapter<Task,TaskListAdapter.ViewHolder>(options)

    fun onLoadData(){
        db.getData()
    }

    fun getRowsCount() : Int{
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun onBindRowViewAtPosition(task: Task, position: Int, holder: TaskListAdapter.ViewHolder) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}