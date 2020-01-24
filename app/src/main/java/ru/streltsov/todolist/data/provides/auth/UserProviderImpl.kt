package ru.streltsov.todolist.data.provides.auth

import ru.streltsov.todolist.data.repository.auth.UserRepositoryImpl

interface UserProviderImpl {
  fun signInByEmail(email:String, password:String, callback: UserRepositoryImpl.UserCallback)
  fun signUpByEmail(email:String, password:String, callback: UserRepositoryImpl.UserCallback)
}