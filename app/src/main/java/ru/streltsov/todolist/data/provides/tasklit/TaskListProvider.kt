package ru.streltsov.todolist.data.provides.tasklit

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import ru.streltsov.todolist.data.provides.BaseProvider
import ru.streltsov.todolist.data.repository.tasklist.TaskListRepositoryImpl
import java.util.*
import javax.inject.Inject

class TaskListProvider @Inject constructor(private val mAuth: FirebaseAuth,
                                           private val db: FirebaseFirestore) : BaseProvider(mAuth, db), TaskListProviderImpl {

  override fun getAllTasks(callback: TaskListRepositoryImpl.Callback) {
    createRequest().orderBy("dateStart").get()
        .addOnCompleteListener{
          if (it.isComplete and it.isSuccessful){
           callback.setTaskList(it.result!!.documents)
          }
          if (it.isCanceled){
            callback.onError(it.exception)
          }
        }

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

  override fun getTasksByDay(day:Date, callback: TaskListRepositoryImpl.Callback) {

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