package ru.streltsov.todolist.data

import android.os.Parcelable
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class LocalStorage : DataBase {
    override fun currentUser(): Parcelable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun login(email: String, password: String): Task<AuthResult> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}