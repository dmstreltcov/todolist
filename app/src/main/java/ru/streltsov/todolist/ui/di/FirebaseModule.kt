package ru.streltsov.todolist.ui.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides

@Module
class FirebaseModule {

    @Provides
    fun provideFirebaseAuth():FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun provideFirebaseFirestore():FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }

}