package ru.streltsov.todolist.ui.di.module

import dagger.Module
import dagger.Provides
import ru.streltsov.todolist.ui.tasklist.TaskListActivity
import ru.streltsov.todolist.ui.tasklist.TaskListAdapter

@Module
class TaskListModule {

    @Provides
    fun provideAdapter():TaskListAdapter{
        return TaskListAdapter()
    }
}