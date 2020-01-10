package ru.streltsov.todolist.ui.di

import dagger.Component
import ru.streltsov.todolist.ui.auth.login.LoginActivity
import ru.streltsov.todolist.ui.auth.singup.SignUpActivity
import ru.streltsov.todolist.ui.task.TaskActivity
import ru.streltsov.todolist.ui.tasklist.TaskListActivity

@Component (modules = [LoginModule::class, SignUpModule::class, TaskModule::class, TaskListModule::class])
interface MyComponent {

    fun inject(activity: SignUpActivity)
    fun inject(activity: LoginActivity)
    fun inject(activity: TaskActivity)
    fun inject(activity: TaskListActivity)

}