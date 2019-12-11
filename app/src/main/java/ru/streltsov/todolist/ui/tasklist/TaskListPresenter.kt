package ru.streltsov.todolist.ui.tasklist

import android.os.Parcelable
import android.util.Log
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.Query
import ru.streltsov.todolist.base.BasePresenter
import ru.streltsov.todolist.data.DataBase
import ru.streltsov.todolist.data.FirebaseDB

class TaskListPresenter : BasePresenter<TaskListView>(), DataBase.Callback {

    private var db: DataBase = FirebaseDB()

    fun onLoadData(){
        view?.showProgressBar()
        db.setCallback(this)
        db.getData()
    }

    fun onChangeStatus(id:String?, boolean: Boolean){
        db.setCallback(this)
        when(boolean){
            true -> db.changeStatus(id!!, boolean)
            false -> db.changeStatus(id!!, boolean)
        }
    }

    override fun returnInfo(message: String) {
        view?.showMessage(message)
    }

    override fun returnData(data: ArrayList<Task>) {
        view?.initAdapter(data)
        view?.hideProgressBar()
    }
}