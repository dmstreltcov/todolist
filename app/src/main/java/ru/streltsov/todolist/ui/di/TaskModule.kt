package ru.streltsov.todolist.ui.di

import dagger.Module
import dagger.Provides
import ru.streltsov.todolist.ui.task.TaskPresenter

@Module
class TaskModule {

    @Provides
    fun provideTaskPresenter():TaskPresenter{
        return TaskPresenter()
    }
}