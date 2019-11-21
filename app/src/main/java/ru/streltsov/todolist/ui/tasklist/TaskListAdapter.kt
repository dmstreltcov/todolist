package ru.streltsov.todolist.ui.tasklist

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Timestamp
import ru.streltsov.todolist.R
import ru.streltsov.todolist.ui.task.TaskActivity
import java.lang.Exception
import java.sql.Date
import java.text.SimpleDateFormat

class TaskListAdapter(private val options: FirestoreRecyclerOptions<Task>) :
    FirestoreRecyclerAdapter<Task, TaskListAdapter.TaskViewHolder>(options) {

    private val TAG: String = "TODO _TaskListAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.tasklist_item_2, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int, task: Task) {
        task.id = snapshots.getSnapshot(position).id
        holder.setData(task)
        Log.d(TAG, "$task")
    }

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val titleView: TextView = itemView.findViewById(R.id.list_item_title)
        private val taskTime:TextView = itemView.findViewById(R.id.task_time)

        fun setData(task: Task) {

            titleView.text = task.title
            taskTime.text = formatDate(task.dateStart)
            itemView.setOnClickListener {
                openTask(task)
            }
        }

        private fun openTask(task: Task) {
            val intent: Intent = Intent(itemView.context, TaskActivity::class.java)
            intent.putExtra("task", task)
            itemView.context.startActivity(intent)
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