package com.smaedev.todolist.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smaedev.todolist.R
import com.smaedev.todolist.databinding.RecyclerviewTasksBinding
import com.smaedev.todolist.entity.Task
import com.smaedev.todolist.task.UpdateTaskActivity

class TasksAdapter(
    private val mCtx: Context,
    private val taskList: List<Task>
) : RecyclerView.Adapter<TasksAdapter.TasksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val view: View =
            LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_tasks, parent, false)
        return TasksViewHolder(view)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val t: Task = taskList[position]

        holder.binding.textViewTask.text = t.taskTitle
        holder.binding.textViewDesc.text = t.description
        holder.binding.textViewFinishBy.text = t.finishBy
        if (t.isFinished == true) {
            holder.binding.textViewStatus.text = holder.itemView.context.getString(R.string.done)
        } else {
            holder.binding.textViewStatus.text = holder.itemView.context.getString(R.string.todo)
            holder.binding.textViewStatus.setTextColor(Color.WHITE)
            holder.binding.textViewStatus.setBackgroundColor(Color.parseColor("#b94e48"))
        }

        holder.binding.oneTask.setOnClickListener{
            val intent = Intent(mCtx, UpdateTaskActivity::class.java)
            intent.putExtra("taskId", t.taskId)
            intent.putExtra("taskTitle", t.taskTitle)
            intent.putExtra("desc", t.description)
            intent.putExtra("finishBy", t.finishBy)
            intent.putExtra("isFinished", t.isFinished)
            mCtx.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    class TasksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding : RecyclerviewTasksBinding = RecyclerviewTasksBinding.bind(itemView)
    }
}