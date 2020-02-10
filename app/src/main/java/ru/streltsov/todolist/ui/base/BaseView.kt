package ru.streltsov.todolist.ui.base

import android.content.Context

interface BaseView {
    fun getContext(): Context
    fun showMessage(message: String)
}