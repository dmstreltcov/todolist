package ru.streltsov.todolist.data.provides.task

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import ru.streltsov.todolist.data.provides.BaseProvider
import ru.streltsov.todolist.data.repository.task.TaskRepositoryImpl
import ru.streltsov.todolist.ui.tasklist.Task
import javax.inject.Inject

class TaskProvider @Inject constructor(
    private val mAuth: FirebaseAuth,
    private val db: FirebaseFirestore) : BaseProvider(), TaskProviderImpl {


  override fun deleteTask(id: String, callback: TaskRepositoryImpl.TaskCallback) {
    db.collection("users").document().collection("tasks")
        .document(id).delete()
        .addOnSuccessListener {
        }
        .addOnFailureListener {
        }
  }

  override fun addTask(task: Task, callback: TaskRepositoryImpl.TaskCallback) {
    db.collection("users").document().collection("tasks")
        .document(task.id!!).set(task)
        .addOnSuccessListener {
        }.addOnFailureListener {
        }
  }

  override fun updateTask(id: String, data: HashMap<String, String>, callback: TaskRepositoryImpl.TaskCallback) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }


}