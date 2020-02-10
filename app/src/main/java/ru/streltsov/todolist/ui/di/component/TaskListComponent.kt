package ru.streltsov.todolist.ui.di.component

import dagger.Subcomponent
import ru.streltsov.todolist.ui.di.module.TaskListModule
import ru.streltsov.todolist.ui.di.scope.TaskListScope
import ru.streltsov.todolist.ui.drawler.home.HomeFragment

@TaskListScope
@Subcomponent(modules = [TaskListModule::class])
interface TaskListComponent {
    fun inject(activity: HomeFragment)
}