package ru.streltsov.todolist.data.provides

import ru.streltsov.todolist.data.repository.UserRepositoryImpl

interface UserProviderImpl {
  fun signInByEmail(email:String, password:String, _callback:UserRepositoryImpl.UserCallback)
  fun signUpByEmail(email:String, password:String, _callback:UserRepositoryImpl.UserCallback)
}