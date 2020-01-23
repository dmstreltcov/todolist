package ru.streltsov.todolist.ui.di.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import ru.streltsov.todolist.data.FirebaseRepository
import ru.streltsov.todolist.data.repository.TaskListRepositoryImpl
import ru.streltsov.todolist.data.repository.TaskRepositoryImpl
import ru.streltsov.todolist.data.repository.UserRepositoryImpl

@Module
class RepositoryModule {

    @Provides
    fun provideUserRepository(mAuth: FirebaseAuth, db: FirebaseFirestore): UserRepositoryImpl {
        return FirebaseRepository(mAuth,db)
    }

    @Provides
    fun provideTaskListRepository(mAuth: FirebaseAuth, db: FirebaseFirestore): TaskListRepositoryImpl {
        return FirebaseRepository(mAuth,db)
    }

    @Provides
    fun provideTaskRepository(mAuth: FirebaseAuth, db: FirebaseFirestore):TaskRepositoryImpl{
        return FirebaseRepository(mAuth,db)
    }

}