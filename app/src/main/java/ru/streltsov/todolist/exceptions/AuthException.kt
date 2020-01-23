package ru.streltsov.todolist.exceptions

import android.util.Log

class AuthException(val exception: Exception) : Exception() {

  override fun printStackTrace() {
    super.printStackTrace()
    Log.e("AuthException", "Error: ", exception)
  }
}