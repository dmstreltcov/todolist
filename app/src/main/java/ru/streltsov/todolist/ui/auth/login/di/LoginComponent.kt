package ru.streltsov.todolist.ui.auth.login.di

import dagger.Component
import ru.streltsov.todolist.MainActivity
import ru.streltsov.todolist.ui.auth.login.LoginActivity

@Component(modules = [LoginModule::class])
interface LoginComponent {
    fun getLoginPresenter(activity: LoginActivity)
}