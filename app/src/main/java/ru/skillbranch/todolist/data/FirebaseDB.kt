package ru.skillbranch.todolist.data

import android.widget.NumberPicker
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class FirebaseDB : DataBase {

    private val mAuth:FirebaseAuth = FirebaseAuth.getInstance()

    override fun login(email:String, password:String) =
        mAuth.signInWithEmailAndPassword(email, password)



}