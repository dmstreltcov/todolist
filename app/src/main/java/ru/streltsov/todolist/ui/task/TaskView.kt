package ru.streltsov.todolist.ui.task

import ru.streltsov.todolist.base.BaseView
import ru.streltsov.todolist.ui.tasklist.Task

interface TaskView : BaseView{
    fun setStartDateText(date:String)
    fun setStartTimeText(time:String)
    fun displayDatePickerDialog(year:Int, month:Int, day:Int)
    fun displayTimePickerDialog(hour:Int, minute:Int)
    fun showData(task:Task)

}