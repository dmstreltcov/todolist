package ru.streltsov.todolist.ui.di.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth():FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun provideCurrentUser(): FirebaseUser {
        return FirebaseAuth.getInstance().currentUser!!
    }

    @Provides
    fun provideFirebaseFirestore():FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }

}