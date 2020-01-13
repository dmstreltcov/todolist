package ru.streltsov.todolist.ui.di

import dagger.Module
import dagger.Provides
import ru.streltsov.todolist.data.FirebaseRepository
import ru.streltsov.todolist.data.repository.TaskListRepository
import ru.streltsov.todolist.data.repository.TaskRepository
import ru.streltsov.todolist.data.repository.UserRepository

@Module
class RepositoryModule {

    @Provides
    fun provideUserRepository(): UserRepository {
        return FirebaseRepository()
    }

    @Provides
    fun provideTaskListRepository(): TaskListRepository {
        return FirebaseRepository()
    }

    @Provides
    fun provideTaskRepository():TaskRepository{
        return FirebaseRepository()
    }

}