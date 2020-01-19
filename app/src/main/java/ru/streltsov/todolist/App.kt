package ru.streltsov.todolist

import android.app.Application
import ru.streltsov.todolist.ui.di.component.AppComponent
import ru.streltsov.todolist.ui.di.component.DaggerAppComponent
import ru.streltsov.todolist.ui.di.component.TaskListComponent
import ru.streltsov.todolist.ui.di.module.TaskListModule

class App : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder().build()
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }

    fun getTaskListComponent(): TaskListComponent =
        appComponent.plus(TaskListModule())

    companion object {
        lateinit var instance: App
            private set
    }
}