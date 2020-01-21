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
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class TaskListAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private val TAG: String = "TodoList/TaskListAdapter"
    //TODO кажется зависимость
    private lateinit var mCallback: Callback
    private lateinit var taskList: List<Task>
    private lateinit var newList:ArrayList<Item>


    //TODO ошибка
    fun setData(list: List<Task>) {
        newList = ArrayList()
        newList = createNewTaskList(list)
        notifyDataSetChanged()
    }

    fun setHeaderDay(timestamp: Timestamp): Item {
        val calendar = Calendar.getInstance()
        calendar.time = timestamp.toDate()
        val month: String = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())!!
        val day: String = calendar.get(Calendar.DATE).toString()
        return Header("$day $month")
    }

    private fun createNewTaskList(list: List<Task>) : ArrayList<Item> {
        val prev = Header("Просроченный")
        val today = Header("Сегодня")
        for (task in list) {
            val day: Item =
                setHeaderDay(Timestamp(task.dateStart!!.seconds, task.dateStart.nanoseconds))
            if (newList.contains(prev) and (task.dateStart.seconds * 1000 < System.currentTimeMillis())) {
                newList.add(task)
            } else if (task.dateStart.seconds * 1000 < System.currentTimeMillis()) {
                newList.add(prev)
                newList.add(task)
            } else if ((task.dateStart.seconds * 1000 > System.currentTimeMillis()) and newList.contains(day)
            ) {
                newList.add(task)
            } else {
                newList.add(day)
                newList.add(task)
            }
        }
        return newList
    }

    interface Callback {
        fun onItemClicked(item: Task)
        fun onStatusChanged(item: Task, status: Boolean)
    }

    fun setCallback(callback: Callback) {
        mCallback = callback
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
                if (item.status) {
                    titleView.paintFlags = titleView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    titleView.text = item.title
                } else {
                    titleView.paintFlags = 0
                    titleView.text = item.title
                }
                if (item.dateStart != null) taskTime.text =
                    formatDate(item.dateStart) else taskTime.text = null
                itemView.setOnClickListener {
                    mCallback.onItemClicked(newList[adapterPosition] as Task)
                }
                statusBox.setOnCheckedChangeListener { _, isChecked ->
                    Log.d(
                        TAG,
                        "${(newList[adapterPosition] as Task).id} + ${(newList[adapterPosition] as Task).status}"
                    )
                    mCallback.onStatusChanged((newList[adapterPosition] as Task), isChecked)
                    notifyItemChanged(adapterPosition)
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
            Log.d(TAG, "isTaskExpired: ${isTaskExpired(dateStart)}")
            return format.format(date)
        } catch (e: Exception) {
            return e.toString()
        }
    }

    override fun getItemCount(): Int {
        return newList.size
    }

    private fun isTaskExpired(endDate: Timestamp): Boolean {
        val toDay: Long = System.currentTimeMillis()
        return (toDay > endDate.seconds * 1000)
    }

}