package ru.streltsov.todolist.ui.tasklist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import ru.streltsov.todolist.R
import java.sql.Date
import java.text.SimpleDateFormat


class TaskListAdapter : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    private val TAG: String = "TodoList/TaskListAdapter"
    //TODO кажется зависимость
    private lateinit var mCallback: Callback
    private lateinit var taskList: List<Task>


    //TODO ошибка
    fun setData(list: List<Task>) {
        taskList = list
        notifyDataSetChanged()
    }

    interface Callback {
        fun onItemClicked(item: Task)
        fun onStatusChanged(item: Task, status: Boolean)
    }

    fun setCallback(callback: Callback) {
        mCallback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TaskViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.tasklist_item_2,
            parent,
            false
        )
    )

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val TAG: String = "TodoList/TaskViewHolder"

        private val titleView: TextView = itemView.findViewById(R.id.list_item_title_2)
        private val taskTime: TextView = itemView.findViewById(R.id.task_time_2)
        private val statusBox: CheckBox = itemView.findViewById(R.id.task_check_box_2)

        fun bind(task: Task) {
            titleView.text = task.title
            statusBox.isChecked = task.status
            if (task.dateStart != null) taskTime.text = formatDate(task.dateStart)
            itemView.setOnClickListener {
                mCallback.onItemClicked(taskList[adapterPosition])
            }
            statusBox.setOnCheckedChangeListener { _, isChecked ->
                Log.d(TAG, "${taskList[adapterPosition].id}")
                mCallback.onStatusChanged(taskList[adapterPosition], isChecked)
            }
        }
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
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
        return taskList.size
    }
}