package ru.streltsov.todolist.data

import android.os.Parcelable
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import ru.streltsov.todolist.data.repository.*
import ru.streltsov.todolist.data.repository.TaskRepository.TaskCallback
import ru.streltsov.todolist.ui.tasklist.Task
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    private val mAuth: FirebaseAuth,
    private val db:FirebaseFirestore) : UserRepository, TaskRepository, TaskListRepository {

    private val TAG: String = "TodoList/Firebase DataBase"

    //TODO зависимость
    private var mList = ArrayList<Task>()

    private fun createRequest(): CollectionReference {
        return db.collection("users").document(mAuth.currentUser!!.uid).collection("tasks")
    }

    /*  Авторизация/Регистрация     */

    override fun login(email: String, password: String, _callback: UserRepository.UserCallback) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _callback.onSuccess()
            } else {
                _callback.onError()
            }
        }
    }

    override fun signUp(email: String, password: String, _callback: UserRepository.UserCallback) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _callback.onSuccess()
            } else {
                _callback.onError()
            }
        }
    }

    /*   TasksListProvider    */

    override fun getAllTasks(_callback: TaskListRepository.TaskListCallback) {
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

    override fun getTasksByDay() {
        TODO("Сделать") //To change body of created functions use File | Settings | File Templates.
    }

    override fun changeStatus(id: String, status: Boolean, _callback: TaskListRepository.TaskListCallback) {
        db.collection("users").document(mAuth.currentUser!!.uid).collection("tasks").document(id)
            .update(
                mapOf(
                    "status" to status
                )
            ).addOnSuccessListener {
                _callback.sendMessage("Красавчик!")
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
                _callback.onSuccess()
            }.addOnFailureListener {
                _callback.onError()
            }
    }

    override fun deleteTask(id: String, _callback: TaskCallback) {
        db.collection("users").document(mAuth.currentUser!!.uid).collection("tasks")
            .document(id).delete().addOnSuccessListener { _callback.onSuccess() }
            .addOnFailureListener {
                _callback.sendInfo()
            }
    }
}