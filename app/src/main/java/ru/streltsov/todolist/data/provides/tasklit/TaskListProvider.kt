package ru.streltsov.todolist.data.provides.tasklit

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import ru.streltsov.todolist.data.provides.BaseProvider
import ru.streltsov.todolist.data.repository.tasklist.TaskListRepositoryImpl
import java.util.*
import javax.inject.Inject

class TaskListProvider @Inject constructor(private val auth: FirebaseAuth,
                                           private val db: FirebaseFirestore) : BaseProvider(), TaskListProviderImpl {

//  @Inject lateinit var currentUserUid: String

  fun createRequest(): CollectionReference {
    Log.e("TaskListProvider", "mAuth = ${auth.currentUser} db = $db")
    return db.collection("users").document(auth.currentUser!!.uid).collection("tasks")
  }

  fun getCurrentUserId(): String {
    return auth.currentUser!!.uid
  }

  override fun getAllTasks(callback: TaskListRepositoryImpl.Callback) {
    Log.e("TaskListProvider", "mAuth = ${auth.currentUser?.uid} db = $db")
    smth().addOnSuccessListener {
      Log.e("TaskListProvider", "${it.documents}")
      callback.setTaskList(it.documents)
  }
//        .addOnCompleteListener{
//          if (it.isComplete and it.isSuccessful){
//           callback.setTaskList(it.result!!.documents)
//          }
//          if (it.isCanceled){
//            callback.onError(it.exception)
//          }
//        }

  /*  createRequest().orderBy("dateStart")

        .addSnapshotListener { snapshot,
                               exception ->
          if (snapshot!!.isEmpty) {
            callback.returnTaskList(mList)
          }

          if (exception != null) {
            callback.sendMessage("Что-то пошло не так")
            Log.d(TAG, "Listen failed. ", exception)
            return@addSnapshotListener
          }

          snapshot.documentChanges.forEachIndexed { index, documentChange ->
            when (documentChange.type) {
              DocumentChange.Type.ADDED -> {
                mList.add(
                    documentChange.newIndex,
                    documentChange.document.toObject(Task::class.java)
                )
                callback.returnTaskList(mList)
              }
              DocumentChange.Type.MODIFIED -> {
                if (documentChange.oldIndex != documentChange.newIndex){
                  mList.removeAt(documentChange.oldIndex)
                  mList.add(
                      documentChange.newIndex,
                      documentChange.document.toObject(Task::class.java)
                  )
                }else{
                  mList[documentChange.newIndex] = documentChange.document.toObject(Task::class.java)
                }

                callback.updateTask(documentChange.oldIndex, documentChange.newIndex) //лишняя какая-то

              }
              DocumentChange.Type.REMOVED -> {
                mList.remove(documentChange.document.toObject(Task::class.java))
                callback.deleteTask(documentChange.oldIndex)
                callback.sendMessage("Задача удалена!")
              }
            }
          }
        }
   */
}


  fun smth(): Task<QuerySnapshot> {
    return createRequest().orderBy("dateStart").get()

  }

override fun getTasksByDay(day: Date, callback: TaskListRepositoryImpl.Callback) {

}

override fun changeTaskStatus(id: String, status: Boolean, callback: TaskListRepositoryImpl.Callback) {
//    db.collection("users").document(mAuth.currentUser!!.uid).collection("tasks").document(id)
//        .update(
//            mapOf(
//                "status" to status
//            )
//        ).addOnSuccessListener {
//          //                callback.sendMessage("Красавчик!")
//        }.addOnFailureListener {
//        }
}

override fun getTaskById(id: String, callback: TaskListRepositoryImpl.Callback) {
//    db.collection("users").document(mAuth.currentUser!!.uid).collection("tasks").document(id)
//        .get()
//        .addOnSuccessListener { documentSnapshot ->
//        }.addOnFailureListener {
//        }
}
}