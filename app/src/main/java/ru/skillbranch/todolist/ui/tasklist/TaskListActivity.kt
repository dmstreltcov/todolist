package ru.skillbranch.todolist.ui.tasklist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseUser
import ru.skillbranch.todolist.R

class TaskListActivity : AppCompatActivity() {

    private lateinit var currentUser:FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        currentUser = intent.getParcelableExtra("currentUser")!! // <- вот тут мне кажется чепуха
    }



}
