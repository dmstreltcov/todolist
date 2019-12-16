package ru.streltsov.todolist.data

interface Validator {
    fun validate(vararg args:String):Boolean
}