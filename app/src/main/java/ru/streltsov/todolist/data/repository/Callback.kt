package ru.streltsov.todolist.data.repository

import ru.streltsov.todolist.ui.tasklist.Task

interface Callback {
    fun onSuccess()
    fun onError()


//    interface Callback{
//        fun returnInfo(message:String)
//        fun returnData(data: ArrayList<Task>)
//        fun updateUI(index:Int)
//    }
}