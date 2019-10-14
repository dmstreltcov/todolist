package ru.skillbranch.todolist.ui.auth.singup

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.skillbranch.todolist.R

class SignUpActivity : AppCompatActivity(), SignUpView {

    private val presenter: SignUpPresenter<SignUpView> by lazy { SignUpPresenter<SignUpView>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        presenter.attach(this)

    }

    private fun init() {

    }

    override fun getContext(): Context = this
}
