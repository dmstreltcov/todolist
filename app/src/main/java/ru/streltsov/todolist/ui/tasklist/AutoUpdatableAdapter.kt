package ru.streltsov.todolist.ui.tasklist

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

interface AutoUpdatableAdapter {
    fun <T> RecyclerView.Adapter<*>.autoNotify(
        oldList: List<T>,
        newList: List<T>,
        compare: () -> Unit
    ) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = oldList[oldItemPosition]
                val newItem = newList[newItemPosition]
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
                val oldItem = oldList[oldItemPosition]
                val newItem = newList[newItemPosition]
                if ((oldItem is Header) and (newItem is Header)){
                    return oldItem == newItem
                }
                if ((oldItem is Task) and (newItem is Task)){
                    return oldItem == newItem
                }
                return false
            }
        })
        diff.dispatchUpdatesTo(this)
    }

}