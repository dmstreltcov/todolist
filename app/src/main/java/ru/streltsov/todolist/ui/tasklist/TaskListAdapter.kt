package ru.streltsov.todolist.ui.tasklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.tasklist_item.view.*
import ru.streltsov.todolist.R

class TaskListAdapter(val presenter: TaskListPresenter, options: FirestoreRecyclerOptions<Task>) :
    FirestoreRecyclerAdapter<Task, TaskListAdapter.ViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.tasklist_item, parent, false
        )
    )

    override fun getItemCount(): Int = presenter.getRowsCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int, task: Task) {
        presenter.onBindRowViewAtPosition(task, position, holder)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), RowView {

        private val titleTextView: TextView = view.list_item_title
        private val descriptionTextView: TextView = view.list_item_description

        override fun setTitle(title: String) {
            titleTextView.text = title
        }

        override fun setDescription(description: String) {
            descriptionTextView.text = description
        }
    }
}