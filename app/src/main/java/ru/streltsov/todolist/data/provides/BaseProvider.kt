package ru.streltsov.todolist.data.provides

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

abstract class BaseProvider{
//  protected fun createRequest(): CollectionReference {
//    Log.e("TaskListProvider", "mAuth = ${getCurrentUserId(mAuth)} db = $db")
//    return db.collection("users").document(mAuth.currentUser!!.uid).collection("tasks")
//  }
//
//  protected fun getCurrentUserId(mAuth: FirebaseAuth): String {
//    Log.e("TaskListProvider", "mAuth = ${mAuth.currentUser!!.uid}")
//    return mAuth.currentUser!!.uid
//  }
}