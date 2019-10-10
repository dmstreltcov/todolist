package ru.skillbranch.todolist.base

interface BaseMvpPresenter<V:BaseView> {
    var isAttached:Boolean
    fun attach(view:V)
    fun detach()
}