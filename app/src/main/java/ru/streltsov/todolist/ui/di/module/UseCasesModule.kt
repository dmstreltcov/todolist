package ru.streltsov.todolist.ui.di.module

import dagger.Module
import dagger.Provides
import ru.streltsov.todolist.data.provides.impl.TaskProvider
import ru.streltsov.todolist.data.provides.impl.UserProvider
import ru.streltsov.todolist.usecases.GetTaskByIdUseCase
import ru.streltsov.todolist.usecases.SignInUseCase

@Module
class UseCasesModule {
    @Provides
    fun signInUseCaseProvide(provider: UserProvider) : SignInUseCase{
        return SignInUseCase(provider)
    }

    @Provides
    fun getTaskByIdUseCaseProvide(provider:TaskProvider) : GetTaskByIdUseCase {
        return GetTaskByIdUseCase(provider)
    }
}