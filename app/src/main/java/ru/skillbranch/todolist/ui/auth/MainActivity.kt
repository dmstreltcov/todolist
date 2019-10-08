package ru.skillbranch.todolist.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.skillbranch.todolist.R

class MainActivity : AppCompatActivity() {

    private val TAG:String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

    }
}
