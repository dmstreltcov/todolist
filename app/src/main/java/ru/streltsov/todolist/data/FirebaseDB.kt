package ru.streltsov.todolist.data

import android.os.Parcelable
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

class FirebaseDB : DataBase {

    private val TAG: String = "Firebase DataBase"
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun currentUser(): Parcelable {
        return mAuth.currentUser!!
    } //Почему-то не нравится вот это

    override fun login(email: String, password: String) =
        mAuth.signInWithEmailAndPassword(email, password)

    override fun signUp(email: String, password: String) =
        mAuth.createUserWithEmailAndPassword(email, password)

    override fun getData(): Query {
        return db.collection("tasks").orderBy("id")
    }

    override fun deleteTask(id: Long){
        db.collection("tasks").whereEqualTo("id", id).get().addOnCompleteListener {
            for (document:QueryDocumentSnapshot in it.result!!){
                document.reference.delete()
            }
        }
    }

}