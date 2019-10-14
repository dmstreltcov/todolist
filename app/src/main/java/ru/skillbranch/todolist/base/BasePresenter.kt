package ru.skillbranch.todolist.base

open class BasePresenter<V:BaseView> {

    protected var view:V? = null
        private set

    var isAttached = view != null

    fun attach(view: V) {
        this.view = view
    }

    fun detach() {
        this.view = null
    }
}