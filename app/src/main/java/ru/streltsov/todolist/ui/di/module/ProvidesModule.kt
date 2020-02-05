package ru.streltsov.todolist.ui.di.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import ru.streltsov.todolist.data.provides.TaskListProviderImpl
import ru.streltsov.todolist.data.provides.TaskProviderImpl
import ru.streltsov.todolist.data.provides.UserProviderImpl
import ru.streltsov.todolist.data.provides.impl.TaskListProvider
import ru.streltsov.todolist.data.provides.impl.TaskProvider
import ru.streltsov.todolist.data.provides.impl.UserProvider

@Module
class ProvidesModule {
    @Provides
    fun provideUserProvider(mAuth: FirebaseAuth): UserProvider {
        return UserProviderImpl(mAuth)
    }

    @Provides
    fun provideTaskListProvider(mAuth: FirebaseAuth, db: FirebaseFirestore): TaskListProvider {
        return TaskListProviderImpl(mAuth, db)
    }

    @Provides
    fun provideTaskProvider(db: FirebaseFirestore): TaskProvider {
        return TaskProviderImpl(db)
    }

}