package ru.streltsov.todolist.data

import android.os.Parcelable
import android.util.Log
import android.widget.NumberPicker
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class FirebaseDB : DataBase {

    private val TAG:String = "Firebase DataBase"
    private val mAuth:FirebaseAuth = FirebaseAuth.getInstance()

    override fun currentUser(): Parcelable {
        return mAuth.currentUser!!
    } //Почему-то не нравится вот это

    override fun login(email:String, password:String) =
        mAuth.signInWithEmailAndPassword(email, password)

    override fun signUp(email:String, password:String) =
        mAuth.createUserWithEmailAndPassword(email, password)

}