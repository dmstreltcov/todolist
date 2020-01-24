package ru.streltsov.todolist.ui.di.module

import dagger.Module
import dagger.Provides
import ru.streltsov.todolist.data.provides.tasklit.TaskListProviderImpl
import ru.streltsov.todolist.data.provides.task.TaskProviderImpl
import ru.streltsov.todolist.data.provides.auth.UserProviderImpl
import ru.streltsov.todolist.data.repository.auth.UserRepository
import ru.streltsov.todolist.data.repository.auth.UserRepositoryImpl
import ru.streltsov.todolist.data.repository.task.TaskRepository
import ru.streltsov.todolist.data.repository.task.TaskRepositoryImpl
import ru.streltsov.todolist.data.repository.tasklist.TaskListRepository
import ru.streltsov.todolist.data.repository.tasklist.TaskListRepositoryImpl

@Module
class RepositoryModule {

    @Provides
    fun provideUserRepository(provider: UserProviderImpl): UserRepositoryImpl {
        return UserRepository(provider)
    }

    @Provides
    fun provideTaskListRepository(provider: TaskListProviderImpl): TaskListRepositoryImpl {
        return TaskListRepository(provider)
    }

    @Provides
    fun provideTaskRepository(provider: TaskProviderImpl): TaskRepositoryImpl {
        return TaskRepository(provider)
    }

}