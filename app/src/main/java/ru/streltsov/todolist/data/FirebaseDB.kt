package ru.streltsov.todolist.data

import android.os.Parcelable
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class FirebaseDB : DataBase {


    private val TAG: String = "TODO _Firebase DataBase"
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun currentUser(): Parcelable {
        return mAuth.currentUser!!
    } //Почему-то не нравится вот это

    override fun login(email: String, password: String) =
        mAuth.signInWithEmailAndPassword(email, password)

    override fun signUp(email: String, password: String) =
        mAuth.createUserWithEmailAndPassword(email, password)

    override fun getData(): Query {
        Log.d(TAG, "Try to get data")
        return db.collection("users").document(mAuth.currentUser!!.uid).collection("tasks").orderBy("createDate")
    }

    override fun deleteTask(id: String?) {
        if (id == null) {
            throw NullPointerException("id is null")
        }
        db.collection("users").document(mAuth.currentUser!!.uid).collection("tasks").document(id.toString()).delete()
    }

    override fun addTask(task: ru.streltsov.todolist.ui.tasklist.Task) {
        Log.d(TAG, mAuth.currentUser!!.uid)
        val data = hashMapOf(
            "title" to task.title,
            "description" to task.description,
            "createDate" to task.createDate,
            "status" to task.status
        )
        db.collection("users").document(mAuth.currentUser!!.uid).collection("tasks").add(data).addOnSuccessListener {
            Log.d(TAG, "TODO _Document written with ID: ${it.id}")
        }.addOnFailureListener {
            Log.w(TAG, "TODO _Error adding document", it)
        }
    }
}