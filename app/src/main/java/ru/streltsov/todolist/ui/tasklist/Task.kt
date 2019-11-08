package ru.streltsov.todolist.ui.tasklist

import com.google.firebase.Timestamp
import java.util.*

data class Task(var id:Long = 0, var title:String="", var description: String="", var timestamp: Timestamp = Timestamp(
    Date()
))