package ru.streltsov.todolist.data.provides

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class TaskListProvider @Inject constructor(private val mAuth: FirebaseAuth, private val db: FirebaseFirestore) : BaseProvider(mAuth, db) {

}