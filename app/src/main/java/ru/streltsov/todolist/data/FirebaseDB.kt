package ru.streltsov.todolist.data

import android.os.Parcelable
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import ru.streltsov.todolist.ui.tasklist.Task as TaskTD

class FirebaseDB : DataBase {


    private val TAG: String = "TodoList/Firebase DataBase"
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var mCallback: DataBase.Callback
    private var mList = ArrayList<TaskTD>()

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
        Log.d(TAG, "Try to get data")
        createRequest().orderBy("createDate").addSnapshotListener { snapshot,
                                                                    exception ->
            if (exception != null) {
                mCallback.returnInfo("Что-то пошло не так")
                Log.d(TAG, "Listen failed. ", exception)
            }
            if (snapshot != null && !snapshot.isEmpty) {
                snapshot.documentChanges.forEachIndexed { index, documentChange ->
                    when (documentChange.type) {
                        DocumentChange.Type.ADDED -> {
                            mList.add(documentChange.newIndex, documentChange.document.toObject(TaskTD::class.java))
                            Log.d(
                                TAG,
                                "Document ${documentChange.document.toObject(TaskTD::class.java).id} added. NewIndex: ${documentChange.newIndex} OldIndex ${documentChange.oldIndex}"
                            )
                        }
                        //TODO косяк тут по всей видимости
                        DocumentChange.Type.MODIFIED -> {
                            mList[documentChange.newIndex] = documentChange.document.toObject(TaskTD::class.java)
                            Log.d(
                                TAG,
                                "Index $index, Data ${documentChange.document.toObject(TaskTD::class.java)} modified. NewIndex: ${documentChange.newIndex} OldIndex ${documentChange.oldIndex}"
                            )
                        }
                        DocumentChange.Type.REMOVED -> {
                            mList.remove(documentChange.document.toObject(TaskTD::class.java))
                            Log.d(
                                TAG,
                                "Index $index, Data ${documentChange.document.toObject(TaskTD::class.java)} removed. NewIndex: ${documentChange.newIndex} OldIndex ${documentChange.oldIndex}"
                            )
                        }
                    }
                }
                mCallback.returnData(mList)
            } else {
                Log.d(TAG, "Data is null")
                mCallback.returnData(mList)
            }
        }
    }
    // createDate
    // dateStart
    // description
    // id
    // status
    // title


    override fun addTask(task: ru.streltsov.todolist.ui.tasklist.Task) {
        Log.d(TAG, mAuth.currentUser!!.uid)
        db.collection("users").document(mAuth.currentUser!!.uid).collection("tasks")
            .document(task.id!!).set(task)
            .addOnSuccessListener {
                mCallback.returnInfo("Задача была создана")
                Log.d(TAG, "Document written with ID: ${task.id}")
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
            .document(id.toString()).delete().addOnSuccessListener {

            }.addOnFailureListener {
                mCallback.returnInfo("Косяк")
            }
    }

    override fun getTaskByID(id: String) {
        db.collection("users").document(mAuth.currentUser!!.uid).collection("tasks").document(id)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val task: TaskTD? = documentSnapshot.toObject(TaskTD::class.java)
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