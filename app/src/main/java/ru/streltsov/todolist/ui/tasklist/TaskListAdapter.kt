package ru.streltsov.todolist.ui.tasklist

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import ru.streltsov.todolist.R
import ru.streltsov.todolist.ui.task.TaskActivity

class TaskListAdapter(private val options: FirestoreRecyclerOptions<Task>) :
    FirestoreRecyclerAdapter<Task, TaskListAdapter.TaskViewHolder>(options) {

    private val TAG: String = "TaskListAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.tasklist_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int, task: Task) {
        holder.setData(task)
        Log.d(TAG, "$task")
    }

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val titleView: TextView = itemView.findViewById(R.id.list_item_title)

        fun setData(task: Task) {
            titleView.text = task.title
            titleView.setOnClickListener {
                openTask(task)
            }
        }

        private fun openTask(task:Task) {
            val intent:Intent = Intent(itemView.context,TaskActivity::class.java)
            intent.putExtra("task",task)
            itemView.context.startActivity(intent)
        }
    }
}