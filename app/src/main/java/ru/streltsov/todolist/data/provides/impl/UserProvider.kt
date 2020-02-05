package ru.streltsov.todolist.data.provides.impl

import ru.streltsov.todolist.ui.base.Callback


interface UserProvider {
  fun signInByEmail(email:String, password:String, callback: Callback)
  fun signUpByEmail(email:String, password:String, callback: Callback)
}