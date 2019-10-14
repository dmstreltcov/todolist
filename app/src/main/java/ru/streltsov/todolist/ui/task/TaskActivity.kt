package ru.streltsov.todolist.ui.task

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.streltsov.todolist.R

class TaskActivity : AppCompatActivity(), TaskView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
    }

    override fun getContext(): Context = this

}
