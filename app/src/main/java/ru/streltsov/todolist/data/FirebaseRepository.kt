package ru.streltsov.todolist.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import ru.streltsov.todolist.data.repository.*
import ru.streltsov.todolist.data.repository.TaskRepositoryImpl.TaskCallback
import ru.streltsov.todolist.ui.tasklist.Task
import javax.inject.Inject

class FirebaseRepository{

    override fun getAllTasks(_callback: TaskListRepositoryImpl.TaskListCallback) {
        createRequest().orderBy("dateStart")

            .addSnapshotListener { snapshot,
                                   exception ->
                if (snapshot!!.isEmpty) {
                    _callback.returnTaskList(mList)
                }

                if (exception != null) {
                    _callback.sendMessage("Что-то пошло не так")
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
                            _callback.returnTaskList(mList)
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

                            _callback.updateTask(documentChange.oldIndex, documentChange.newIndex) //лишняя какая-то

                        }
                        DocumentChange.Type.REMOVED -> {
                            mList.remove(documentChange.document.toObject(Task::class.java))
                            _callback.deleteTask(documentChange.oldIndex)
                            _callback.sendMessage("Задача удалена!")
                        }
                    }
                }
            }
    }

    fun getAllTask(): ArrayList<Task> {
        val list = ArrayList<Task>()
        createRequest().orderBy("dateStart").get()
            .addOnSuccessListener { result ->
                for(document in result){
                    list.add(document.toObject(Task::class.java))
                }
            }
            .addOnFailureListener{ exception -> Log.e(TAG, "Error: ", exception) }
        Log.d(TAG, "${list}")
        return list
    }

    override fun getTasksByDay() {
        val tasks = getAllTask()
        Log.d(TAG, "${tasks}")
    }

    override fun changeStatus(id: String, status: Boolean, _callback: TaskListRepositoryImpl.TaskListCallback) {
        db.collection("users").document(mAuth.currentUser!!.uid).collection("tasks").document(id)
            .update(
                mapOf(
                    "status" to status
                )
            ).addOnSuccessListener {
//                _callback.sendMessage("Красавчик!")
            }.addOnFailureListener {
                _callback.sendMessage("Что-то пошло не так")
            }
    }

    override fun getTaskById(id: String, _callback:TaskCallback) {
        db.collection("users").document(mAuth.currentUser!!.uid).collection("tasks").document(id)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val task: Task = documentSnapshot.toObject(Task::class.java)!!
                _callback.returnTask(task)
            }.addOnFailureListener {
                Log.d(TAG, "$it")
            }
    }

    /*   TaskProvider    */

    override fun addTask(task: Task, _callback: TaskCallback) {
        db.collection("users").document(mAuth.currentUser!!.uid).collection("tasks")
            .document(task.id!!).set(task)
            .addOnSuccessListener {
                _callback.onSuccess(it.user.uid)
            }.addOnFailureListener {
                _callback.onError(it)
            }
    }

    override fun deleteTask(id: String, _callback: TaskCallback) {
        db.collection("users").document(mAuth.currentUser!!.uid).collection("tasks")
            .document(id).delete().addOnSuccessListener { _callback.onSuccess(it.user.uid) }
            .addOnFailureListener {
                _callback.sendInfo()
            }
    }
}