package ru.streltsov.todolist.data.provides

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import ru.streltsov.todolist.data.provides.impl.TaskProvider
import ru.streltsov.todolist.ui.base.Callback
import ru.streltsov.todolist.ui.tasklist.Task
import javax.inject.Inject

class TaskProviderImpl @Inject constructor(private val db: FirebaseFirestore, private val user:FirebaseUser) : TaskProvider{

    override fun deleteTask(id: String) {
        db.collection("users").document(user.uid).collection("tasks")
            .document(id).delete()
            .addOnSuccessListener {
            }
            .addOnFailureListener {
            }
    }

    override fun addTask(task: Task, callback: Callback) {
        db.collection("users").document(user.uid).collection("tasks")
            .document(task.id!!).set(task)
            .addOnSuccessListener {
                callback.onSuccess()
            }.addOnFailureListener {
                callback.onError(it)
            }
    }

    override fun updateTask(id: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTaskById(id: String, callback: Callback.TaskCallback) {

    }


}