package ru.skillbranch.todolist.ui.tasklist

import android.os.Parcelable
import ru.skillbranch.todolist.base.BasePresenter
import ru.skillbranch.todolist.base.BaseView

abstract class ITaskListPresenter<T> :BasePresenter<BaseView>() {
    abstract fun onLoadData(currentUser : Parcelable)
}