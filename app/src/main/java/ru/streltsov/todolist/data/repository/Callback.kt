package ru.streltsov.todolist.data.repository

interface Callback {
    fun onSuccess()
    fun onError()
}