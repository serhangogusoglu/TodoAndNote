package com.example.todoandnote.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoandnote.data.Note
import com.example.todoandnote.data.Task
import com.example.todoandnote.databinding.ItemTaskBinding
import com.example.todoandnote.ui.TaskDetailActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TaskAdapter(private var taskList: List<Task>, private val onDeleteClick: (Task) -> Unit) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.binding.tvTaskTitle. text = task.title
        holder.binding.tvTaskDescription.text = task.description
        holder.binding.tvTaskTime.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(task.timestamp))

        holder.binding.ivDelete.setOnClickListener {
            onDeleteClick(task)
        }

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, TaskDetailActivity::class.java).apply {
                putExtra("title", task.title)
                putExtra("description", task.description)
                putExtra("timestamp", task.timestamp)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = taskList.size

    fun updateTasks(newTasks: List<Task>) {
        taskList = newTasks
        notifyDataSetChanged()
    }
}
