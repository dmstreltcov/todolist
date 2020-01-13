package ru.streltsov.todolist.ui.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import ru.streltsov.todolist.data.FirebaseRepository
import ru.streltsov.todolist.data.repository.TaskListRepository
import ru.streltsov.todolist.data.repository.TaskRepository
import ru.streltsov.todolist.data.repository.UserRepository

@Module
class RepositoryModule {

    @Provides
    fun provideUserRepository(mAuth: FirebaseAuth, db: FirebaseFirestore): UserRepository {
        return FirebaseRepository(mAuth,db)
    }

    @Provides
    fun provideTaskListRepository(mAuth: FirebaseAuth, db: FirebaseFirestore): TaskListRepository {
        return FirebaseRepository(mAuth,db)
    }

    @Provides
    fun provideTaskRepository(mAuth: FirebaseAuth, db: FirebaseFirestore):TaskRepository{
        return FirebaseRepository(mAuth,db)
    }

}