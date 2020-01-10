package ru.streltsov.todolist.data.di

import dagger.Component
import ru.streltsov.todolist.ui.auth.login.LoginPresenter

@Component(modules = [FirebaseModule::class])
interface FirebaseComponent {

    fun getUserRepository(loginPresenter: LoginPresenter)
}