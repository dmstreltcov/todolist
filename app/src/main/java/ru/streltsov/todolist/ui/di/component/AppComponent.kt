package ru.streltsov.todolist.ui.di.component

import dagger.Component
import ru.streltsov.todolist.MainActivity
import ru.streltsov.todolist.ui.auth.login.LoginActivity
import ru.streltsov.todolist.ui.auth.singup.SignUpActivity
import ru.streltsov.todolist.ui.di.module.AppModule
import ru.streltsov.todolist.ui.di.module.FirebaseModule
import ru.streltsov.todolist.ui.di.module.RepositoryModule
import ru.streltsov.todolist.ui.di.module.TaskListModule
import ru.streltsov.todolist.ui.task.TaskActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RepositoryModule::class, FirebaseModule::class])
interface AppComponent {

    fun plus(module: TaskListModule): TaskListComponent

    fun inject(activity: MainActivity)
    fun inject(activity: TaskActivity)
    fun inject(activity: LoginActivity)
    fun inject(activity: SignUpActivity)

}