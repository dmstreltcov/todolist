package ru.streltsov.todolist.ui.tasklist

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Timestamp
import ru.streltsov.todolist.R
import ru.streltsov.todolist.ui.task.TaskActivity
import java.sql.Date
import java.text.SimpleDateFormat


class TaskListAdapter(private val options: FirestoreRecyclerOptions<Task>) :
    FirestoreRecyclerAdapter<Task, TaskListAdapter.TaskViewHolder>(options) {

    private val TAG: String = "TodoList/TaskListAdapter"
    private lateinit var mCallback: Callback

    interface Callback {
        fun onItemClicked(item: Task)
        fun onStatusChanged(item: Task, status:Boolean)
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

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int, task: Task) {
        holder.setData(task)
        Log.d(TAG, "$task")
    }

   inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val TAG: String = "TodoList/TaskViewHolder"

        private val titleView: TextView = itemView.findViewById(R.id.list_item_title_2)
        private val taskTime: TextView = itemView.findViewById(R.id.task_time_2)
        private val statusBox: CheckBox = itemView.findViewById(R.id.task_check_box_2)

        fun setData(task: Task) {
            titleView.text = task.title
            if (task.dateStart != null) taskTime.text = formatDate(task.dateStart)
            statusBox.isChecked = task.status
            itemView.setOnClickListener {
                mCallback.onItemClicked(options.snapshots[adapterPosition])
            }
            statusBox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                mCallback.onStatusChanged(options.snapshots[adapterPosition], isChecked)
            })
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
    }
}