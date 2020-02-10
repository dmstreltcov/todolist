package ru.streltsov.todolist.ui.tasklist

class Header(val header: String) : Item {
    override fun equals(other: Any?): Boolean {
        if (other is Header) {
            if (header == other.header) {
                return true
            }
        }
        return false
    }
}