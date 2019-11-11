package ru.streltsov.todolist.ui.tasklist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import ru.streltsov.todolist.R

class TaskListAdapter(private val options: FirestoreRecyclerOptions<Task>) :
    FirestoreRecyclerAdapter<Task, TaskListAdapter.TaskViewHolder>(options) {

    private val TAG: String = "TaskListAdapter"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.tasklist_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int, task: Task) {
        holder.setTask(task.title, task.description)
        Log.d(TAG, "$task")
    }

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun setTask(title: String, description: String) {
            val titleView: TextView = itemView.findViewById(R.id.list_item_title)
            titleView.text = title
        }

    }
}