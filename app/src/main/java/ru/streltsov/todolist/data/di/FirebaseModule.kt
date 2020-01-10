package ru.streltsov.todolist.data.di

import dagger.Module
import dagger.Provides
import ru.streltsov.todolist.data.FirebaseRepository
import ru.streltsov.todolist.data.repository.Callback
import ru.streltsov.todolist.data.repository.UserRepository
import ru.streltsov.todolist.ui.auth.login.LoginPresenter

@Module
class FirebaseModule {

    @Provides
    fun provideFirebaseRepository(mCallback:Callback):UserRepository{
        return FirebaseRepository(mCallback)
    }

    @Provides
    fun provideCallback():Callback{
        return LoginPresenter()
    }
}