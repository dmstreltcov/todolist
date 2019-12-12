package ru.streltsov.todolist.data

import android.os.Parcelable
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import ru.streltsov.todolist.ui.tasklist.Task

private const val TAG: String = "TodoList/Firebase DataBase"

class FirebaseDB : DataBase {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var mCallback: DataBase.Callback
    private var mList = ArrayList<Task>()

    override fun currentUser(): Parcelable {
        return mAuth.currentUser!!
    } //Почему-то не нравится вот это

    private fun createRequest(): CollectionReference {
        return db.collection("users").document(mAuth.currentUser!!.uid).collection("tasks")
    }

    override fun login(email: String, password: String) =
        mAuth.signInWithEmailAndPassword(email, password)

    override fun signUp(email: String, password: String) =
        mAuth.createUserWithEmailAndPassword(email, password)

    override fun getData() {
        createRequest().orderBy("createDate").addSnapshotListener { snapshot,
                                                                    exception ->

            if (exception != null) {
                mCallback.returnInfo("Что-то пошло не так")
                Log.d(TAG, "Listen failed. ", exception)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                snapshot.documentChanges.forEachIndexed { index, documentChange ->
                    when (documentChange.type) {
                        DocumentChange.Type.ADDED -> {
                            mList.add(documentChange.newIndex, documentChange.document.toObject(Task::class.java))
                            mCallback.returnData(mList)
                        }
                        DocumentChange.Type.MODIFIED -> {
                            mList[documentChange.newIndex] = documentChange.document.toObject(Task::class.java)
                        }
                        DocumentChange.Type.REMOVED -> {
                            mList.remove(documentChange.document.toObject(Task::class.java))
                            mCallback.updateUI(documentChange.oldIndex) //лишняя какая-то
                            mCallback.returnInfo("Задача удалена!")
                        }
                    }
                }

            } else {
                //TODO удали
                Log.d(TAG, "Data is $mList")
                mCallback.returnData(mList)
            }
        }
    }

    override fun addTask(task: ru.streltsov.todolist.ui.tasklist.Task) {
        db.collection("users").document(mAuth.currentUser!!.uid).collection("tasks")
            .document(task.id!!).set(task)
            .addOnSuccessListener {
                mCallback.returnInfo("Задача сохранена")
                Log.d(TAG, "Document written.")
            }.addOnFailureListener {
                mCallback.returnInfo("Возникла ошибка. Попробуйте снова")
                Log.w(TAG, "Error adding document", it)
            }
    }

    override fun deleteTask(id: String?) {
        if (id == null) {
            throw NullPointerException("id is null")
        }
        db.collection("users").document(mAuth.currentUser!!.uid).collection("tasks")
            .document(id.toString()).delete().addOnFailureListener {
                mCallback.returnInfo("Косяк")
            }
    }

    override fun getTaskByID(id: String) {
        db.collection("users").document(mAuth.currentUser!!.uid).collection("tasks").document(id)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val task: Task? = documentSnapshot.toObject(Task::class.java)
                mCallback.returnData(arrayListOf(task!!))
            }.addOnFailureListener {
                Log.d(TAG, "$it")
            }
    }

    override fun changeStatus(id: String, status: Boolean) {
        db.collection("users").document(mAuth.currentUser!!.uid).collection("tasks").document(id)
            .update(
                mapOf(
                    "status" to status
                )
            ).addOnSuccessListener {
                mCallback.returnInfo("Красавчик!")
            }.addOnFailureListener {
                mCallback.returnInfo("Что-то пошло не так")
            }
    }

    override fun setCallback(callback: DataBase.Callback) {
        mCallback = callback
    }

    fun getAllAlarm() {

    }
}