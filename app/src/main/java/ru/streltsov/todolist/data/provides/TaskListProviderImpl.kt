package ru.streltsov.todolist.data.provides

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import ru.streltsov.todolist.data.provides.impl.TaskListProvider
import ru.streltsov.todolist.ui.base.Callback
import ru.streltsov.todolist.ui.tasklist.Task
import java.lang.Exception
import javax.inject.Inject

class TaskListProviderImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : TaskListProvider {

    fun createRequest(): CollectionReference {
        Log.e("TaskListProvider", "mAuth = ${auth.currentUser} db = $db")
        return db.collection("users").document(auth.currentUser!!.uid).collection("tasks")
    }

    override fun getAllTasks(callback: Callback.TaskListCallback) {
        val list:ArrayList<Task> = ArrayList()
        createRequest().orderBy("createDate").addSnapshotListener { snapshot, exception ->
            if (exception != null){
                Log.w("EXCEPTION", exception)
                return@addSnapshotListener
            }

            for (doc in snapshot!!.documentChanges){
                when(doc.type){
                    DocumentChange.Type.ADDED -> callback.returnList(snapshot.documents.map { it.toObject(Task::class.java) } as ArrayList<Task>)
                    DocumentChange.Type.MODIFIED -> callback.returnList(snapshot.documents.map { it.toObject(Task::class.java) } as ArrayList<Task>)
                    DocumentChange.Type.REMOVED ->callback.returnList(snapshot.documents.map { it.toObject(Task::class.java) } as ArrayList<Task>)
                }
            }

        }









//            .addOnSuccessListener {
//                for (document in it.documents){
//                    list.add(document.toObject(Task::class.java)!!)
//                }
//                callback.returnList(list)
//            }
    }




    override fun getTasksByDay() {

    }

    override fun changeTaskStatus() {
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

    override fun getTaskById() {
//    db.collection("users").document(mAuth.currentUser!!.uid).collection("tasks").document(id)
//        .get()
//        .addOnSuccessListener { documentSnapshot ->
//        }.addOnFailureListener {
//        }
    }
}