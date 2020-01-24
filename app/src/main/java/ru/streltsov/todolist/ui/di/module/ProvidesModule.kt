package ru.streltsov.todolist.ui.di.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import ru.streltsov.todolist.data.provides.auth.UserProvider
import ru.streltsov.todolist.data.provides.auth.UserProviderImpl
import ru.streltsov.todolist.data.provides.task.TaskProvider
import ru.streltsov.todolist.data.provides.task.TaskProviderImpl
import ru.streltsov.todolist.data.provides.tasklit.TaskListProvider
import ru.streltsov.todolist.data.provides.tasklit.TaskListProviderImpl

@Module
class ProvidesModule {
  @Provides
  fun provideUserProvider(mAuth: FirebaseAuth): UserProviderImpl {
    return UserProvider(mAuth)
  }

  @Provides
  fun provideTaskListProvider(mAuth: FirebaseAuth, db: FirebaseFirestore): TaskListProviderImpl {
    return TaskListProvider(mAuth, db)
  }

  @Provides
  fun provideTaskProvider(mAuth: FirebaseAuth, db: FirebaseFirestore) : TaskProviderImpl {
    return TaskProvider(mAuth, db)
  }

}