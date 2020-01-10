package ru.streltsov.todolist.ui.di

import dagger.Module
import dagger.Provides
import ru.streltsov.todolist.ui.tasklist.TaskListPresenter

@Module
class TaskListModule {

    @Provides
    fun provideTaskListPresenter():TaskListPresenter{
        return TaskListPresenter()
    }
}