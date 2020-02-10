package ru.streltsov.todolist.ui.utils

import androidx.recyclerview.widget.DiffUtil
import ru.streltsov.todolist.ui.tasklist.Header
import ru.streltsov.todolist.ui.tasklist.Item
import ru.streltsov.todolist.ui.tasklist.Task

class ItemsDiffUtils(val oldList: List<Item>, val newList:List<Item>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem:Item = oldList[oldItemPosition]
        val newItem:Item = newList[newItemPosition]
        if ((oldItem is Header) and (newItem is Header)){
            return oldItem == newItem
        }
        if ((oldItem is Task) and (newItem is Task)){
            return oldItem == newItem
        }
        return false
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem:Item = oldList[oldItemPosition]
        val newItem:Item = newList[newItemPosition]
        if ((oldItem is Header) and (newItem is Header)){
          return oldItem == newItem
        }
        if ((oldItem is Task) and (newItem is Task)){
            return oldItem == newItem
        }
        return false
    }
}