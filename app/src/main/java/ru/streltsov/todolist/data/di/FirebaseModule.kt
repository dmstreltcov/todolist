package ru.streltsov.todolist.data.di

import dagger.Module
import dagger.Provides
import ru.streltsov.todolist.data.FirebaseRepository
import ru.streltsov.todolist.data.repository.Callback
import ru.streltsov.todolist.data.repository.UserRepository
import ru.streltsov.todolist.ui.auth.login.LoginPresenter


class FirebaseModule {


    fun provideFirebaseRepository(mCallback:Callback):UserRepository{
        return FirebaseRepository()
    }
    fun provideCallback():Callback{
        return LoginPresenter()
    }
}