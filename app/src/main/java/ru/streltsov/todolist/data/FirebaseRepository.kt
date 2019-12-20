package ru.streltsov.todolist.data

import android.os.Parcelable
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import ru.streltsov.todolist.data.repository.Callback
import ru.streltsov.todolist.data.repository.DatabaseRepository
import ru.streltsov.todolist.data.repository.TaskListRepository
import ru.streltsov.todolist.data.repository.TaskRepository
import ru.streltsov.todolist.data.repository.TaskRepository.TaskCallback
import ru.streltsov.todolist.ui.tasklist.Task

class FirebaseRepository(private val mCallback: Callback) : DatabaseRepository {
    private val TAG: String = "TodoList/Firebase DataBase"
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var mList = ArrayList<Task>()

    private fun createRequest(): CollectionReference {
        return db.collection("users").document(mAuth.currentUser!!.uid).collection("tasks")
    }

    /*  Авторизация/Регистрация     */

    override fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                mCallback.onSuccess()
            } else {
                mCallback.onError()
            }
        }
    }

    override fun signUp(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                mCallback.onSuccess()
            } else {
                mCallback.onError()
            }
        }
    }

    /*   TasksListProvider    */

    override fun getAllTasks() {
        createRequest().orderBy("dateStart")

            .addSnapshotListener { snapshot,
                                   exception ->
                if (snapshot!!.isEmpty) {
                    (mCallback as TaskListRepository.TaskListCallback).returnTaskList(mList)
                }

                if (exception != null) {
                    (mCallback as TaskListRepository.TaskListCallback).sendMessage("Что-то пошло не так")
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
                            (mCallback as TaskListRepository.TaskListCallback).returnTaskList(mList)
                        }
                        DocumentChange.Type.MODIFIED -> {
                            mList[documentChange.newIndex] =
                                documentChange.document.toObject(Task::class.java)
                        }
                        DocumentChange.Type.REMOVED -> {
                            mList.remove(documentChange.document.toObject(Task::class.java))
                            (mCallback as TaskListRepository.TaskListCallback).updateUI(documentChange.oldIndex) //лишняя какая-то
                            (mCallback as TaskListRepository.TaskListCallback).sendMessage("Задача удалена!")
                        }
                    }
                }
            }
    }

    override fun getTasksByDay() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun changeStatus(id: String, status: Boolean) {
        db.collection("users").document(mAuth.currentUser!!.uid).collection("tasks").document(id)
            .update(
                mapOf(
                    "status" to status
                )
            ).addOnSuccessListener {
                (mCallback as TaskListRepository.TaskListCallback).sendMessage("Красавчик!")
            }.addOnFailureListener {
                (mCallback as TaskListRepository.TaskListCallback).sendMessage("Что-то пошло не так")
            }
    }

    override fun getTaskById(id: String) {
        db.collection("users").document(mAuth.currentUser!!.uid).collection("tasks").document(id)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val task: Task = documentSnapshot.toObject(Task::class.java)!!
                (mCallback as TaskRepository.TaskCallback).returnTask(task)
            }.addOnFailureListener {
                Log.d(TAG, "$it")
            }
    }

    /*   TaskProvider    */

    override fun addTask(task: Task) {
        db.collection("users").document(mAuth.currentUser!!.uid).collection("tasks")
            .document(task.id!!).set(task)
            .addOnSuccessListener {
                mCallback.onSuccess()
            }.addOnFailureListener {
                mCallback.onError()
            }
    }

    override fun updateTask() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteTask(id: String) {
        db.collection("users").document(mAuth.currentUser!!.uid).collection("tasks")
            .document(id).delete().addOnSuccessListener { mCallback.onSuccess() }
            .addOnFailureListener {
                (mCallback as TaskCallback).sendInfo()
            }
    }

    override fun setCallback(callback: Callback) {
//        mCallback = callback
    }
}