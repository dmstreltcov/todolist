package ru.skillbranch.todolist.ui.auth.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.skillbranch.todolist.R

class LoginActivity : AppCompatActivity(), LoginView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


    }
}
