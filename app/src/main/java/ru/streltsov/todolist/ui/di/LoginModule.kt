package ru.streltsov.todolist.ui.di

import dagger.Module
import dagger.Provides
import ru.streltsov.todolist.ui.auth.login.LoginPresenter

@Module
class LoginModule {

    @Provides
    fun provideLoginPresenter():LoginPresenter{
        return LoginPresenter()
    }

}