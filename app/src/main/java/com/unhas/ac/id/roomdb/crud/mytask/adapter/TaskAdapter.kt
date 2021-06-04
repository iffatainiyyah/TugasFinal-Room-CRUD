package com.unhas.ac.id.roomdb.crud.mytask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.unhas.ac.id.roomdb.crud.mytask.databinding.ListItemBinding
import com.unhas.ac.id.roomdb.crud.mytask.R 
import com.unhas.ac.id.roomdb.crud.mytask.db.Task


class TaskAdapter(private val taskList: List<Task>, private val clickListener: (Task)->Unit)
    : RecyclerView.Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.listitem_task, parent, false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position], clickListener)
    }

}
class TaskViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(task: Task, clickListener: (Task)->Unit) {
        binding.nameTextView.text = task.name
        binding.dateTextView.text = task.date

        binding.listItemLayout.setOnClickListener{
            clickListener(task)
        }
    }
}