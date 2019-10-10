package ru.skillbranch.todolist.data

import android.os.Parcelable
import android.widget.NumberPicker
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class FirebaseDB : DataBase {

    private val mAuth:FirebaseAuth = FirebaseAuth.getInstance()

    override fun currentUser(): Parcelable {
        return mAuth.currentUser!!
    }

    override fun login(email:String, password:String) =
        mAuth.signInWithEmailAndPassword(email, password)
}