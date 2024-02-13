package com.todo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.todo.OnClickListener
import com.todo.databinding.ItemTaskBinding
import com.todo.models.Task

class TasksAdapter(var tasks:List<Task>?=null) :Adapter<TasksAdapter.TasksViewHolder>(){
    var onDeletedClickListener:OnClickListener?=null
    var onTaskClickListener:OnClickListener?=null

    class TasksViewHolder(val binding: ItemTaskBinding):ViewHolder(binding.root){
        fun bind(task: Task){
            binding.title.text=task.title
            binding.time.text=task.date.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {

        val inflater=LayoutInflater.from(parent.context)
        val binding=ItemTaskBinding.inflate(inflater,parent,false)
        return TasksViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return tasks?.size?:0
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val item:Task= tasks!![position]
        holder.bind(item)
        holder.binding.leftView.setOnClickListener{
            onDeletedClickListener?.onClick(item,position)

        }
        holder.binding.dragItem.setOnClickListener {
            onTaskClickListener?.onClick(item, position)
        }

    }
    fun updateData(tasks: List<Task>){
        this.tasks=tasks
        notifyDataSetChanged()
    }


    }

