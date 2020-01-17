package ru.streltsov.todolist.ui.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.streltsov.todolist.App
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return app
    }

}