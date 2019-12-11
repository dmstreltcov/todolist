package ru.streltsov.todolist.data

import android.os.Parcelable
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

class LocalStorage : DataBase {
    override fun addTask(task: ru.streltsov.todolist.ui.tasklist.Task) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTaskByID(id: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun changeStatus(id: String, status: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setCallback(callback: DataBase.Callback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteTask(id:String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun signUp(email: String, password: String): Task<AuthResult> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun currentUser(): Parcelable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun login(email: String, password: String): Task<AuthResult> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}