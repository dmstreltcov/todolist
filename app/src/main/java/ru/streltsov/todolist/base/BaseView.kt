package ru.streltsov.todolist.base

import android.content.Context

interface BaseView {
    fun getContext(): Context
    fun showError(message: String)
}