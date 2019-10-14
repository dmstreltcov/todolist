package ru.streltsov.todolist.ui.tasklist

import android.os.Parcelable
import ru.streltsov.todolist.base.BasePresenter
import ru.streltsov.todolist.base.BaseView

abstract class ITaskListPresenter<T> :BasePresenter<BaseView>() {
    abstract fun onLoadData(currentUser : Parcelable)
}