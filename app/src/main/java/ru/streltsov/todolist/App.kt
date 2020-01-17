package ru.streltsov.todolist

import android.annotation.SuppressLint
import android.app.Application
import ru.streltsov.todolist.ui.di.component.AppComponent
import ru.streltsov.todolist.ui.di.component.DaggerAppComponent
import ru.streltsov.todolist.ui.di.component.TaskListComponent
import ru.streltsov.todolist.ui.di.module.TaskListModule

@SuppressLint("Registered")
class App : Application() {

    private lateinit var appComponent: AppComponent

    private fun initDagger(): App {
        appComponent = DaggerAppComponent.builder().build()
        return this
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }

    fun getTaskListComponent(): TaskListComponent =
        appComponent.plus(TaskListModule())

    companion object {
        val instance: App = App().initDagger()
    }
}