package ru.streltsov.todolist.ui.tasklist

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import ru.streltsov.todolist.R
import ru.streltsov.todolist.ui.utils.TaskListUtils
import java.sql.Date
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList


class TaskListAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private val TAG: String = "TodoList/TaskListAdapter"
    //TODO кажется зависимость
    private lateinit var callback: Callback
    private lateinit var newList:ArrayList<Item>

    //TODO ошибка
    fun setData(list: List<Task>) {
        newList = TaskListUtils().createNewTaskList(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            0 -> TaskViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.tasklist_item_2,
                    parent,
                    false
                )
            )
            else -> HeaderViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.tasklist_item,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (newList[position]) {
            is Task -> 0
            else -> 1
        }
    }

    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {

        private val header: TextView = itemView.findViewById(R.id.header)

        override fun bind(item: Item) {
            header.text = (item as Header).header
        }

    }

    inner class TaskViewHolder(view: View) : BaseViewHolder(view) {
        private val TAG: String = "TodoList/TaskViewHolder"

        private val titleView: TextView = itemView.findViewById(R.id.list_item_title_2)
        private val taskTime: TextView = itemView.findViewById(R.id.task_time_2)
        private val statusBox: CheckBox = itemView.findViewById(R.id.task_check_box_2)

        override fun bind(item: Item) {
            if (item is Task) {
                titleView.text = item.title
                if (item.status) {
                    titleView.paintFlags = titleView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    titleView.paintFlags = 0
                }
                if (item.dateStart != null) taskTime.text =
                    formatDate(item.dateStart) else taskTime.text = null
                itemView.setOnClickListener {
                    callback.onItemClicked(item)
                }
                statusBox.setOnCheckedChangeListener { _, isChecked ->
                    Log.d(TAG,"${(item).id} + ${(item).status} + $adapterPosition")
                    callback.onStatusChanged(item, isChecked, adapterPosition)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(newList[position])
    }

    private fun formatDate(dateStart: Timestamp?): String {
        try {
            val format = SimpleDateFormat("HH:mm dd MMM")
            val date = Date(dateStart!!.seconds * 1000)
            return format.format(date)
        } catch (e: Exception) {
            return e.toString()
        }
    }

    override fun getItemCount(): Int {
        return newList.size
    }

    interface Callback {
        fun onItemClicked(item: Task)
        fun onStatusChanged(item: Task, status: Boolean, position:Int)
    }

    fun setCallback(callback: Callback) {
        this.callback = callback
    }

}