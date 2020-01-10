package ru.streltsov.todolist.ui.di

import dagger.Module
import dagger.Provides
import ru.streltsov.todolist.ui.auth.singup.SignUpPresenter

@Module
class SignUpModule {

    @Provides
    fun provideSignUpPresenter():SignUpPresenter{
        return SignUpPresenter()
    }
}