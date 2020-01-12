package ru.streltsov.todolist.ui.di

import dagger.Module
import dagger.Provides
import ru.streltsov.todolist.data.FirebaseRepository
import ru.streltsov.todolist.data.repository.TaskListRepository
import ru.streltsov.todolist.ui.tasklist.TaskListPresenter

@Module
class TaskListModule {

    @Provides
    fun provideTaskListPresenter():TaskListPresenter{
        return TaskListPresenter()
    }

    @Provides
    fun provideTaskListRepository(): TaskListRepository {
        return FirebaseRepository()
    }

}