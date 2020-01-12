package ru.streltsov.todolist.ui.di

import dagger.Component
import ru.streltsov.todolist.data.repository.UserRepository
import ru.streltsov.todolist.ui.auth.login.LoginActivity
import ru.streltsov.todolist.ui.auth.login.LoginPresenter
import ru.streltsov.todolist.ui.auth.singup.SignUpActivity
import ru.streltsov.todolist.ui.task.TaskActivity
import ru.streltsov.todolist.ui.tasklist.TaskListActivity
import ru.streltsov.todolist.ui.tasklist.TaskListPresenter
import javax.security.auth.callback.Callback

@Component (modules = [LoginModule::class, SignUpModule::class, TaskModule::class, TaskListModule::class])
interface MyComponent {

    fun inject(activity: SignUpActivity)
    fun inject(activity: LoginActivity)
    fun inject(activity: TaskActivity)
    fun inject(activity: TaskListActivity)

    fun inject(presenter: LoginPresenter)
    fun inject(presenter: TaskListPresenter)

}