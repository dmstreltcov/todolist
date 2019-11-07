package ru.streltsov.todolist.data

import android.os.Parcelable
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.Query

class LocalStorage : DataBase {
    override fun getData(): Query {
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