package ru.streltsov.todolist.data.provides

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

abstract class BaseProvider @Inject constructor(
    private val mAuth: FirebaseAuth,
    private val db: FirebaseFirestore
) {
  protected fun createRequest() : CollectionReference{
    return db.collection("users").document(mAuth.currentUser!!.uid).collection("tasks")
  }
}