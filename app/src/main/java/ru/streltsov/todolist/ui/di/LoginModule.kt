package ru.streltsov.todolist.ui.di

import dagger.Module
import dagger.Provides
import ru.streltsov.todolist.data.FirebaseRepository
import ru.streltsov.todolist.data.repository.Callback
import ru.streltsov.todolist.data.repository.UserRepository
import ru.streltsov.todolist.ui.auth.login.LoginPresenter
import javax.inject.Singleton

@Module
class LoginModule {

    @Provides
    fun provideLoginPresenter():LoginPresenter{
        return LoginPresenter()
    }

    @Provides
    fun provideUserRepository():UserRepository{
        return FirebaseRepository()
    }

}